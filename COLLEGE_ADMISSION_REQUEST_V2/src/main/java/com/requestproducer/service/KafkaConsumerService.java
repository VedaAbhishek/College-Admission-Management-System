package com.requestproducer.service;

import com.requestproducer.entity.CollegeAdmission;
import com.requestproducer.utils.ApplicationUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.requestproducer.constants.ApplicationConstants.ADMISSION_REQUEST_TOPIC;
import static com.requestproducer.routes.ProducerRoute.fileName;

@Service
@Log4j2
public class KafkaConsumerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private CollegeService collegeService;
    @Autowired
    private ProducerTemplate producerTemplate;
    @Autowired
    private ApplicationUtils applicationUtils;

    @KafkaListener(topics = ADMISSION_REQUEST_TOPIC, groupId = "admission-group")
    public void consumeMessage(CollegeAdmission collegeAdmission) {

        Long numberOfInterestedStreams = collegeService.getNumberOfInterestedStreams(collegeAdmission.getInterestedStream());
        log.info("Available entries for {} stream entries {}", collegeAdmission.getInterestedStream(), numberOfInterestedStreams);
        if (numberOfInterestedStreams > 5) {
            log.info("Currently admissions are filled for {}, please try another stream.",
                    collegeAdmission.getInterestedStream());
        }
        // Save the message to the database using Spring Data JPA
        else {

            int count = collegeService.updateDatabaseStatus(collegeAdmission);
            CollegeAdmission updatedAdmission = collegeService.findEntryByHallTicketNumber(collegeAdmission.getHallTicketNumber());
            if(updatedAdmission != null) {
                log.info("processed with effected rows {} and record {}", count, updatedAdmission);
                producerTemplate.sendBody(applicationUtils.getDestinationLocation() + "?fileName=" + fileName + "-processed.csv",
                        convertPojoToCsv(updatedAdmission));
                log.info("CSV file generated successfully");
            }
            else{
                log.info("We regret to inform that your candidature will not be moved forward");
            }
        }
    }

    private String convertPojoToCsv(CollegeAdmission collegeAdmission) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Name : ").append(collegeAdmission.getFirstName() + " " + collegeAdmission.getLastName()+ System.lineSeparator())
                .append("Selected Stream : ").append(collegeAdmission.getInterestedStream() + System.lineSeparator())
                .append("Status").append(collegeAdmission.getStatus());

        return csvContent.toString();
    }
}
