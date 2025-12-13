package project.task.manager.notification_service.kafka.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDeserializer {
	
	private final ObjectMapper objectMapper;
	
	public <T> T fromKafkaMessage(Message<?> message, Class<T> clazz) {
		try {
			String jsonStr = extractJsonFromPayload(message.getPayload());
			return objectMapper.readValue(jsonStr, clazz);
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert Kafka message to object of type " + clazz.getSimpleName(), e);
		}
	}
	
	private boolean isBase64(String str) {
		if (str == null || str.trim().isEmpty()) {
			return false;
		}
		try {
			Base64.getDecoder().decode(str.trim());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	private String extractJsonFromPayload(Object payload) {
		String payloadStr;
		
		if (payload instanceof byte[]) {
			payloadStr = new String((byte[]) payload, StandardCharsets.UTF_8);
		} else if (payload instanceof String) {
			payloadStr = (String) payload;
		} else {
			throw new IllegalArgumentException("Unsupported payload type: " + payload.getClass());
		}
		
		try {
			if (isBase64(payloadStr)) {
				byte[] decodedBytes = Base64.getDecoder().decode(payloadStr);
				return new String(decodedBytes, StandardCharsets.UTF_8);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("can't decode from base64: " + payload.getClass());
		}
		return payloadStr;
	}
}