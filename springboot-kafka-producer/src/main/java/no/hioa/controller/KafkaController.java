package no.hioa.controller;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import no.hioa.model.User;
import no.hioa.avro.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;



@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	//@Autowired
	//@Qualifier("avro")
	@Resource(name="avro")
	private KafkaTemplate<String, Order> kafkaTemplateAvro;
	
	//@Autowired
	//@Qualifier("json")
	@Resource(name="json")
	private KafkaTemplate<String, User> kafkaTemplateJson;
	
	private static final String TOPIC ="testmessage-topic"; // should start zookeeper, kafka, create topic

	@GetMapping("/send/{name}")
	public String sendData(@PathVariable("name") final String name) {
		
		this.kafkaTemplateJson.send(TOPIC, new User(name, "HSBC", 800000, "cs123"));  // publish the message to topics.
		
		return "Published succesfully - All consumer of testmessage-topic must have received an message -" + name;
	}
	
	@RequestMapping(value = "/json", method=RequestMethod.POST)
	@ResponseBody
	public String sendJsonData(@RequestBody final User jsonData) throws JsonProcessingException, InterruptedException {
		String status  = "";
		String prettyJson = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(jsonData);
		System.out.println("Seding Message..."+"\n"+prettyJson);
		
		ListenableFuture<SendResult<String, User>> sendFutuer = this.kafkaTemplateJson.send("test-json-1605", jsonData);  // publish the message to topics.
		while(!sendFutuer.isDone()) {
			Thread.sleep(2000);
			System.out.println("Sending....");
		}
		CompletableFuture<SendResult<String, User>> comp =	sendFutuer.completable();
		if(comp.isDone()) {
			status = "Published succesfully - All consumer of testmessage-topic must have received an message -" + jsonData;
		} else {
			status = "Published failed";
		}
		System.out.println("\n"+status);
		return status;
	}
	
	@RequestMapping(value = "/avro", method=RequestMethod.POST)
	@ResponseBody
	public String sendAvroData(@RequestBody final User jsonData) throws JsonProcessingException, InterruptedException {
		String status  = "";
		String prettyJson = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(jsonData);
		System.out.println("Seding Message..."+"\n"+prettyJson);
		
		Order order = Order.newBuilder()
				.setOrderId("OId234")
				.setCustomerId(jsonData.getCustId())
				.setSupplierId("SId543")
				.setItems(4)
				.setFirstName(jsonData.getName())
				.setLastName(jsonData.getName()+" LN")
				.setPrice(178f)
				.setWeight(75f)
				.build();
		
		ListenableFuture<SendResult<String, Order>> sendFutuer = this.kafkaTemplateAvro.send("test-json-1705", jsonData.getCustId(), order);
		while(!sendFutuer.isDone()) {
			Thread.sleep(2000);
			System.out.println("Sending....");
		}
		CompletableFuture<SendResult<String, Order>> comp =	sendFutuer.completable();
		if(comp.isDone()) {
			status = "Published succesfully - All consumer of testmessage-topic must have received an message -" + jsonData;
		} else {
			status = "Published failed";
		}
		System.out.println("\n"+status);
		return status;
	}
	
	
}
