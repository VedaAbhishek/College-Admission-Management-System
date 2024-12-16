package com.requestproducer.controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.requestproducer.entity.CollegeAdmission;
import com.requestproducer.service.CollegeService;
import com.requestproducer.utils.ApplicationUtils;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Log4j2
public class AdmissionController {
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private ApplicationUtils applicationUtils;
    @Autowired
    private ProducerTemplate producerTemplate;

    @GetMapping("/requests")
    public List<CollegeAdmission> getAllRequests() {
        return collegeService.getAllEntities();
    }

    @PostMapping("/process/admission")
    public String admissionRequest(@Valid @RequestBody CollegeAdmission collegeAdmission) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            String endpointUri = applicationUtils.getSourceLocation() +
                    "?fileName=AdmissionRequest-" +
                    increment() +
                    ".json";

            String jsonString = objectMapper
                    .writer(new DefaultPrettyPrinter())
                    .writeValueAsString(collegeAdmission);
            producerTemplate.sendBody(endpointUri,
                    jsonString);

            log.info("saved into {}", endpointUri);
            return "JSON data saved to file";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving JSON data";
        }
    }

    static AtomicInteger integer = new AtomicInteger(1);

    public static int increment() {
        return integer.getAndIncrement();
    }
}
