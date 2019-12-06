package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.service.YikondiKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yikondi-kafka")
public class YikondiKafkaResource {

    private final Logger log = LoggerFactory.getLogger(YikondiKafkaResource.class);

    private YikondiKafkaProducer kafkaProducer;

    public YikondiKafkaResource(YikondiKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
