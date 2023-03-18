package me.kimsoar.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    @KafkaListener(topics = "javaguides", groupId = "myGroup")
    public void consume(String message){
        log.info(String.format("Message received -> %s", message));
    }
}
