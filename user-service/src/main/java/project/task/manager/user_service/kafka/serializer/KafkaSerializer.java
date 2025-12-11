package project.task.manager.user_service.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.exception.SerializationException;

import java.util.Collections;
import java.util.Map;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSerializer {
	
	private final ObjectMapper objectMapper;

	/**
	 * Сериализует объект в Kafka сообщение
	 */
	public <T> Message<byte[]> serialize(String topic, String key, T object) {
		return serialize(topic, key, object, Collections.emptyMap());
	}

	/**
	 * Сериализует объект в Kafka сообщение с дополнительными заголовками
	 */
	public <T> Message<byte[]> serialize(String topic, String key, T object, Map<String, Object> headers) {
		validateMessageParameters(topic, key, object);

		try {
			byte[] payload = objectMapper.writeValueAsBytes(object);

			MessageBuilder<byte[]> builder = createMessageBuilder(payload, topic, key);
			addObjectTypeHeader(builder, object);
			addCustomHeaders(builder, headers);

			return builder.build();

		} catch (JsonProcessingException e) {
			log.error("Failed to serialize object to Kafka message. Topic: {}, Key: {}, Type: {}",
					topic, key, object.getClass().getSimpleName(), e);
			throw new SerializationException("Failed to serialize object to Kafka message", e);
		}
	}
	
	/**
	 * Создает MessageBuilder с базовыми заголовками Kafka
	 */
	private MessageBuilder<byte[]> createMessageBuilder(byte[] payload, String topic, String key) {
		return MessageBuilder.withPayload(payload)
				.setHeader(KafkaHeaders.TOPIC, topic)
				.setHeader(KafkaHeaders.KEY, key);
	}
	
	/**
	 * Добавляет пользовательские заголовки в MessageBuilder
	 */
	private void addCustomHeaders(MessageBuilder<byte[]> builder, Map<String, Object> headers) {
		if (headers != null) {
			headers.forEach(builder::setHeader);
		}
	}
	
	/**
	 * Добавляет заголовок с типом объекта
	 */
	private <T> void addObjectTypeHeader(MessageBuilder<byte[]> builder, T object) {
		builder.setHeader(Constants.OBJECT_TYPE_HEADER, object.getClass().getSimpleName());
	}
	
	/**
	 * Валидирует обязательные параметры для создания сообщения
	 */
	private void validateMessageParameters(String topic, String key, Object object) {
		if (topic == null || topic.trim().isEmpty()) {
			log.error("Topic cannot be null or empty");
			throw new IllegalArgumentException("Topic cannot be null or empty");
		}
		if (key == null || key.trim().isEmpty()) {
			log.error("Key cannot be null or empty");
			throw new IllegalArgumentException("Key cannot be null or empty");
		}
		if (object == null) {
			log.error("Object cannot be null");
			throw new IllegalArgumentException("Object cannot be null");
		}
	}
}