package no.hioa.processor;

import javax.annotation.PostConstruct;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamProcessor {

	@Value("${kafka.topic.input}")
	private String inputTopic;

	@Value("${kafka.topic.even-output}")
	private String evenOutputTopic;
	
	@Value("${kafka.topic.odd-output}")
	private String oddOutputTopic;
	

//	@Autowired
//	@Qualifier("raw")
//	KafkaStreams kafkaStreams;
//	
//	@PostConstruct
//	public void process() {
//		System.out.println("==========> started ");
//		this.kafkaStreams.state();
//	}
	
	@Bean
	public KStream<String, Long> kStream(StreamsBuilder kStreamBuilder) {
		KStream<String, Long> stream = kStreamBuilder.stream(inputTopic);  
//		this.evenNumber(stream);
//		this.oddNumber(stream);
		this.evenNumberHandle(stream);
		return stream;
	}
	
	/**
	 * 
	 * @param stream
	 * @return
	 */
	private KStream<String, Long> evenNumber(KStream<String, Long> stream) {
		stream.filter((k, v) -> v % 2 == 0)
		.mapValues(v -> {
			System.out.println("Processing Even Number :: " + v);
			return v * v;
		})
		.to(evenOutputTopic);
		return stream;
	}
	
	private KStream<String, Long> evenNumberHandle(KStream<String, Long> stream) {
		stream.filter((k, v) -> v % 2 == 0)
		.mapValues(v -> {
			try {
				System.out.println("Processing Even Number :: " + v);
				if(v == 8){
					return 8+"cc";
				}
				
			} catch (Exception e) {
				System.out.println("= = = = > Exception: "+ e.getMessage());
				throw e;
			}
			return v;
		})
		.to(evenOutputTopic);
		return stream;
	}
	
	
	/**
	 * 
	 * @param stream
	 * @return
	 */
	private KStream<String, Long> oddNumber(KStream<String, Long> stream) {
		stream.filter((k,v) -> v % 2 != 0)
		.mapValues(v -> {
			System.out.println("Process Odd Number: "+ v);
			return v + v; 
		}).to(oddOutputTopic);
		return stream;
	}
}
