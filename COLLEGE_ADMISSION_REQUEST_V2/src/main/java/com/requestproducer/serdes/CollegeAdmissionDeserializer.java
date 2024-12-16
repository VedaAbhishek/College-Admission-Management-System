package com.requestproducer.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.requestproducer.entity.CollegeAdmission;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class CollegeAdmissionDeserializer implements Deserializer<CollegeAdmission> {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Override
    public CollegeAdmission deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, CollegeAdmission.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing CollegeAdmission", e);
        }
    }
}
