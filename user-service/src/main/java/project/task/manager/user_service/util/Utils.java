package project.task.manager.user_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.enums.EventType;
import project.task.manager.user_service.data.event.BaseEvent;
import project.task.manager.user_service.data.event.UserUpdatedEvent;
import project.task.manager.user_service.kafka.properties.KafkaTopicProperties;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class Utils {
	
	private final KafkaTopicProperties kafkaTopicProperties;
	private final ObjectMapper objectMapper;
		
		public String mapRandomPassword() {
				String random = UUID.randomUUID().toString();

				char[] randomCharArray = random.toCharArray();
				randomCharArray[1] = '!';
				randomCharArray[3] = '%';
				randomCharArray[5] = 'F';
				randomCharArray[7] = 'H';

				StringBuilder sb = new StringBuilder();

				for(char c : randomCharArray) {
						sb.append(c);
				}
				return sb.toString().substring(0, 9);
		}
	
	public String getTopicName(EventType eventType) {
		switch(eventType) {
			case USER_UPDATED -> {
				return kafkaTopicProperties.topics().userUpdated().name();
			}
			case USER_DELETED -> {
				return kafkaTopicProperties.topics().userDeleted().name();
			}
			default -> {
				return kafkaTopicProperties.topics().userCreated().name();
			}
		}
	}
	
	public BaseEvent getEventPayload(Outbox outbox) {
		try {
			var payload = outbox.getPayload().toString();
			UserUpdatedEvent event = objectMapper.readValue(payload, UserUpdatedEvent.class);
			event.setMessageId(outbox.getId());
			return event;
		} catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}