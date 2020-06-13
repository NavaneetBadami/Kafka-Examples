package no.hioa.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSquareConsumer {

	@KafkaListener(topics = "${kafka.topic.even-output}")
    public void consumeEvenNumber(Long number)  {
        System.out.println(String.format("Consumed EvenProcessed:: %d", number));
    }
	

	@KafkaListener(topics = "${kafka.topic.odd-output}")
    public void consumeOddNumber(Long number)  {
        System.out.println(String.format("Consumed OddProcessed:: %d", number));
    }
	
}
