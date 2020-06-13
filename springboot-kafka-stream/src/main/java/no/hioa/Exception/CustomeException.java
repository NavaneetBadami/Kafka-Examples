package no.hioa.Exception;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

public class CustomeException implements DeserializationExceptionHandler{

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub

	}

	@Override
	public DeserializationHandlerResponse handle(final ProcessorContext context, 
			final ConsumerRecord<byte[], byte[]> record, final Exception exception) {
		System.out.println("Exception caught during Deserialization, sending to the dead queue topic; " +
				"taskId: "+ context.taskId() +" topic: " +record.topic()+ ", partition: "+ record.partition() +" "+exception.getMessage());

		return DeserializationHandlerResponse.CONTINUE;
	}

}
