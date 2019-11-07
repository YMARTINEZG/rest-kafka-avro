package com.confluent.example.restkafkaavro;

import com.confluent.develop.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${topic.name}")
    private String TOPIC;

    private final KafkaTemplate<String, Customer> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, Customer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    void sendMessage(Customer customer){
        this.kafkaTemplate.send(TOPIC,customer);
        logger.info(String.format("Producer Customer -> %s", customer));
    }
}
