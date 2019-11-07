package com.confluent.example.restkafkaavro;

import com.confluent.develop.Address;
import com.confluent.develop.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/event")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final Producer producer;

    @Autowired
    public MainController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(path="/customer", consumes= {MediaType.APPLICATION_JSON_VALUE})
    public void sendCustomerToKafkaTopic(@RequestBody Event event){
        logger.info("event  :" + event.toString());
        Customer customer = Customer.newBuilder()
                .setFirstName(event.getFirstName())
                .setLastName(event.getLastName())
                .setSsn(event.getSsn())
                .setCustomerid(event.getAccountid())
                .setAddressList(new ArrayList<>())
                .build();
        this.producer.sendMessage(customer);
    }

    @PostMapping(path="/address", consumes= {MediaType.APPLICATION_JSON_VALUE})
    public void sendAddressToKafkaTopic(@RequestBody Event event){
        logger.info("event  :" + event.toString());
        Address address = new Address(event.getAddress1(), event.getCity(), event.getZipcode());
        Customer customer = Customer.newBuilder()
                .setCustomerid(event.getAccountid())
                .setAddressList(Arrays.asList(address))
                .build();
        this.producer.sendMessage(customer);
    }
}
