package com.requestproducer.routes;

import com.requestproducer.service.KafkaConsumerService;
import com.requestproducer.service.KafkaProducerService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.requestproducer.constants.ApplicationConstants.*;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class ConsumerRoute extends RouteBuilder {
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private KafkaConsumerService kafkaConsumerService;
    @Override
    public void configure() throws Exception {
        from(COMMON_ENDPOINT)
                .routeId(CONSUMER_ROUTE + " triggered")
                .bean(kafkaProducerService)
                .log(INFO,"Payload received, publishing to Kafka Topic " + ADMISSION_REQUEST_TOPIC)

                .bean(kafkaConsumerService, "consumeMessage");
    }
}
