package com.group.kafka.kafkaconfluent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.kafka.kafkaconfluent.model.User;

@RestController
@RequestMapping(value = "/user") 
public class Controller {
	
	 private final Producer producer;

	  @Autowired
	  Controller(Producer producer) {
	    this.producer = producer;
	  }

	  @PostMapping(value = "/publish")
	  public void sendMessageToKafkaTopic(@RequestParam("name") String name, @RequestParam("age") Integer age) {
	    this.producer.sendMessage(new User(name,age));
	  }
}
