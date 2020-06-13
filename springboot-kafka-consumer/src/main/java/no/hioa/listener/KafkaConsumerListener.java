package no.hioa.listener;

import no.hioa.avro.Order;
import no.hioa.model.User;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class KafkaConsumerListener {
	
	// default containerFactory = "kafkaListenerContainerFactory"
	@KafkaListener(topics="test-kafka", groupId ="group_id")
	public void consume(String message) {
		System.out.println("consumed: "+ message);
	}
	
	@KafkaListener(topics="test-kafka-json0604", groupId ="group_json", containerFactory="userKafkaListenerContainerFactory")
	public void consumeJson(@RequestBody User json) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
		String prettyJson = mapper.writeValueAsString(json);
		System.out.println("Consumer-1 Message Received:");
		System.out.println(prettyJson);
	}
	/*We cannot have more consumers within a group(group_json) than partition(1) for Topic*/
	@KafkaListener(topics="test-kafka-json0604", groupId ="group_json", containerFactory="userKafkaListenerContainerFactory")
	public void consumeJson2(@RequestBody User json) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
		String prettyJson = mapper.writeValueAsString(json);
		System.out.println("Consumer-2 Message Received:");
		System.out.println(prettyJson);
	}
	/* group 2 */
	@KafkaListener(topics="test-kafka-json0604", groupId ="group_json2", containerFactory="userKafkaListenerContainerFactory2")
	public void consumeJson3(@RequestBody User json) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
		String prettyJson = mapper.writeValueAsString(json);
		System.out.println("Consumer-3 Message Received:");
		System.out.println(prettyJson);
	}
	
	@KafkaListener(topics="test-json-1705", groupId ="group_avro", containerFactory="userKafkaListenerContainerFactoryAvro")
	public void consumeAvro(Order order) throws JsonProcessingException {
		System.out.println("============> Data Received: " + order.toString());
	}
	
//	public void consumeJson2(@RequestBody User json) throws JsonProcessingException {
//		Properties props = new Properties();
//		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
//		props.put("group.id", "test");
//		props.put("enable.auto.commit", "true");
//		props.put("auto.commit.interval.ms", "1000");
//		props.put("session.timeout.ms", "30000");
//		props.put("key.deserializer", "org.apache.kafka.common.serializa-tion.StringDeserializer");
//		props.put("value.deserializer", "org.apache.kafka.common.serializa-tion.StringDeserializer");
//		KafkaConsumer<String, String> consumer = new KafkaConsumer <String, String>(props);
//		
//		//Kafka Consumer subscribes list of topics here.
//	      consumer.subscribe(Arrays.asList("test-kafka-json2903"));
//	      while (true) {
//	          ConsumerRecords<String, String> records = consumer.poll(100);
//	          for (ConsumerRecord<String, String> record : records)
//	          
//	          // print the offset,key and value for the consumer records.
//	          System.out.printf("offset = %d, key = %s, value = %s\n", 
//	             record.offset(), record.key(), record.value());
//	       }
//	}	
}
