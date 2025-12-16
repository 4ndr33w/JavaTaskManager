package project.task.manager.notification_service.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import org.springframework.stereotype.Component;
import project.task.manager.notification_service.exception.KafkaDeserializationException;
import project.task.manager.notification_service.exception.LoadClassException;
import project.task.manager.notification_service.properties.NotificationKafkaProperties;
import project.task.manager.notification_service.properties.data.NotificationTypeMapping;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(NotificationKafkaProperties.class)
public class TaskManagerDeserializer extends ErrorHandlingDeserializer<Object> {
	
	private final ObjectMapper objectMapper;
	private final NotificationKafkaProperties properties;
	private Map<String, Class<?>> notificationTypeMapping = new HashMap<>();
	
	/**
	 * Конфигурирует десериализатор при инициализации.
	 * @param configs Конфигурационные параметры Kafka
	 * @param isKey Флаг, указывающий, что десериализуется ключ (true) или значение (false)
	 */
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		super.configure(configs, isKey);
		notificationTypeMapping = getNotificationTypeMapping();
	}
	
	/**
	 * Строит маппинг типов уведомлений на основе конфигурации.
	 * @return Map<String, Class<?>>, где ключ - тип из конфигурации,
	 *         а значение - соответствующий класс
	 */
	private Map<String, Class<?>> getNotificationTypeMapping() {
		Map<String, Class<?>> mapping = new HashMap<>();
		for (NotificationTypeMapping ntm : properties.getNotificationTypeMappingList()) {
			try {
				Class<?> clazz = Class.forName(ntm.className());
				mapping.put(ntm.type(), clazz);
			} catch (ClassNotFoundException e) {
				throw new LoadClassException("Failed to load class: " + ntm.className(), e);
			}
		}
		return mapping;
	}
	
	/**
	 * Десериализует сообщение из байтового массива в объект.
	 * @param topic Топик Kafka, из которого пришло сообщение
	 * @param data Данные сообщения в виде байтового массива
	 * @return Десериализованный объект или null, если data == null
	 */
	@Override
	public Object deserialize(String topic, Headers headers, byte[] data) {
		if (data == null) {
			return null;
		}
		String messageType = extractMessageType(headers);
		try {
			Class<?> targetClass = notificationTypeMapping.get(messageType);
			if (targetClass == null) {
				log.error("Unknown message type: " + messageType);
				throw new KafkaDeserializationException("Unknown message type: " + messageType);
			}
			return objectMapper.treeToValue(objectMapper.readTree(data), targetClass);
		}
		catch (Exception ex) {
			log.error("Failed to deserialize message: " + messageType, ex);
			throw new KafkaDeserializationException("Failed to deserialize message", ex);
		}
	}
	
	private String extractMessageType(Headers headers) {
		if (headers == null) return null;
		Header typeHeader = headers.lastHeader(properties.getHeaderTypeName());
		return typeHeader != null
				? new String(typeHeader.value(), StandardCharsets.UTF_8)
				: null;
	}
}