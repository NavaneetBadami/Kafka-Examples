package no.hioa.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import java.util.HashMap;
import java.util.Map;

import no.hioa.avro.Order;
import no.hioa.model.User;
import no.hioa.converter.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConfig {
	
	// STRING MESSAGE
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		
		Map<String, Object> config = new HashMap<String, Object>();
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://ec2-15-206-163-101.ap-south-1.compute.amazonaws.com:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<String, String>(config);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(this.consumerFactory());
		return concurrentKafkaListenerContainerFactory;
	}
	
	
	// JSON MESSAGE
	
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory(){
		
		Map<String, Object> config = new HashMap<String, Object>();
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<String, User>(config, new StringDeserializer(), new JsonDeserializer<>(User.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, User>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(this.userConsumerFactory());
		return concurrentKafkaListenerContainerFactory;
	}
	
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory2(){
		
		Map<String, Object> config = new HashMap<String, Object>();
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json2");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<String, User>(config, new StringDeserializer(), new JsonDeserializer<>(User.class));
	}
		

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory2() {
		ConcurrentKafkaListenerContainerFactory<String, User> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, User>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(this.userConsumerFactory2());
		return concurrentKafkaListenerContainerFactory;
	}
	
	@Bean
	public ConsumerFactory<String, Order> userConsumerFactoryAvro(){
		
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_avro");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		config.put("schema.registry.url", "http://localhost:8081"); //http://schema-registry:8081

		return new DefaultKafkaConsumerFactory<String, Order>
		(config, new StringDeserializer(), new AvroDeserializer<>(Order.class));  // AVRO DeSerializier
	} 
		
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Order> userKafkaListenerContainerFactoryAvro() {
		ConcurrentKafkaListenerContainerFactory<String, Order> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, Order>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(this.userConsumerFactoryAvro());
		return concurrentKafkaListenerContainerFactory;
	}
	
	
}	
