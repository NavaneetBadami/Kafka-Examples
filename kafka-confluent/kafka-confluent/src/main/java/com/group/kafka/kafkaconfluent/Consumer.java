package com.group.kafka.kafkaconfluent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.group.kafka.kafkaconfluent.model.User;

@Service
public class Consumer {

	 @Value("${topic.name}")
	  private String topicName;

	  @KafkaListener(topics = "users", groupId = "group_id")
	  public void consume(ConsumerRecord<String, User> record) {
	    System.out.println(String.format("Consumed message -> %s", record.value()));
	  }
	
}


