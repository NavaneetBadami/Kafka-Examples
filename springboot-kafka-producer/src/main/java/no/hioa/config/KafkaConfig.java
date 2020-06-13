package no.hioa.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.HashMap;
import java.util.Map;

import no.hioa.model.User;

import org.apache.avro.Schema;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

	@Bean
	public ProducerFactory<String, User> producerFactory(){
		
		Map<String, Object> config = new HashMap<String, Object>();
//		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://ec2-15-206-163-101.ap-south-1.compute.amazonaws.com:9092");
		config.put(ProducerConfig.ACKS_CONFIG, "0");
	//	config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, Partiti);
		config.put(ProducerConfig.RETRIES_CONFIG, 0);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<String, User>(config);
	}
	
	
	@Bean(name="json")
	public KafkaTemplate<String, User> kafkaTemplate () {
		
		return new KafkaTemplate<String, User>(this.producerFactory());		// pass custom config details 
	}
	
	@Bean
	public ProducerFactory<String, User> producerFactoryAvro(){
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.ACKS_CONFIG, "0");
		config.put(ProducerConfig.RETRIES_CONFIG, 10);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
		config.put("schema.registry.url", "http://localhost:8081"); //http://schema-registry:8081
		return new DefaultKafkaProducerFactory<String, User>(config);
	}
	
	@Bean(name="avro")
	public KafkaTemplate<String, User> kafkaTemplateAvro() {		
		return new KafkaTemplate<String, User>(this.producerFactoryAvro());		// pass custom config details 
	}
	
}
