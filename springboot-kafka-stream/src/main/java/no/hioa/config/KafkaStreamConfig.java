package no.hioa.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import no.hioa.Exception.CustomeException;
import no.hioa.Exception.CustomProductionExceptionHandler;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
		Map<String, Object> config = new HashMap<>();
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
		config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, CustomeException.class.getName());
		config.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG, CustomProductionExceptionHandler.class.getName());
		return new KafkaStreamsConfiguration(config);
	}
	
	/* Runnig two sprint two streams */
//	@Bean(name="raw")
//	public KafkaStreams kStreamsConfigs2(KafkaProperties kafkaProperties) {
//		final Properties config = new Properties();
//		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//		config.put(StreamsConfig.APPLICATION_ID_CONFIG, "square-string");
//		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//		config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
//		config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, CustomeException.class.getName());
//		
//		final StreamsBuilder builder = new StreamsBuilder();	
////		builder.stream("string-input", Consumed.with(Serdes.String(), Serdes.String()))
////		/*builder.stream("string-input")*/.mapValues(v -> {
////			String value = (String) v;
////			System.out.println("Processing = = = = = = = => "+ value);
////			return value.toUpperCase();
////		}).to("string-output");
//		
////		builder.stream("string-input").map((k,v) -> {
////			String value = (String) v;
////			System.out.println("Processing K V = = = = = = = => "+ value);
////			KeyValue<Object, String> keyValue = new KeyValue<>(k, value.toUpperCase());
////			return keyValue;
////		}).to("string-output");
//		
//		 builder.stream("string-input")
//		.flatMapValues(value -> Arrays.asList(value.toString().split("_")).stream()
//				.map(vv -> new KeyValue<>(vv, vv))
//				.collect(Collectors.toList()))
//				.to("string-output");
//		 
//		KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), config);
//		kafkaStreams.start();
//		return kafkaStreams;
//	}
}
