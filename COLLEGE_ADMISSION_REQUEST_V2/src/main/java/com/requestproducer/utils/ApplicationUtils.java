package com.requestproducer.utils;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationUtils {

    @Value("${camel.sourceLocation}")
    private String sourceLocation;
    @Value("${camel.destinationLocation}")
    private String destinationLocation;
}
