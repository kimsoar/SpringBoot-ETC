package me.kimsoar.kafka;

import me.kimsoar.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "javaguides_json", groupId = "myGroup")
    public void consume(User data){
        log.info(String.format("Message received -> %s", data));
    }
}
