package com.requestproducer.service;

import com.requestproducer.entity.CollegeAdmission;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.requestproducer.constants.ApplicationConstants.ADMISSION_REQUEST_TOPIC;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private CollegeService collegeService;

    @Handler
    public void sendToKafkaAndUpdateDatabase(CollegeAdmission collegeAdmission) {
        if (!collegeService.checkDuplicateHallTicketEntry(collegeAdmission.getHallTicketNumber())
                && !collegeService.checkDuplicateEmailEntry(collegeAdmission.getEmail())) {
            collegeAdmission.setStatus("PROCESSING");
            log.info("Updating payload into Database");
            collegeService.saveAdmission(collegeAdmission);
            log.info("Updated payload into Database successfully, payload --> \n" + collegeAdmission);
            kafkaTemplate.send(ADMISSION_REQUEST_TOPIC, collegeAdmission);
            log.info("Published payload onto Kafka topic {}", ADMISSION_REQUEST_TOPIC);

        } else {
            log.info("Record already Exists");
        }
    }
}
