package com.requestproducer.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.requestproducer.entity.CollegeAdmission;
import org.apache.kafka.common.serialization.Serializer;

public class CollegeAdmissionSerializer implements Serializer<CollegeAdmission> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, CollegeAdmission collegeAdmission) {
        try {
            objectMapper.registerModule(new JavaTimeModule())
                    .findAndRegisterModules()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return objectMapper.writeValueAsBytes(collegeAdmission);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing CollegeAdmission", e);
        }
    }
}
