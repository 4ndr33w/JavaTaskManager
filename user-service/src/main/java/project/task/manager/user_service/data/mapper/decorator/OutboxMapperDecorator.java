package project.task.manager.user_service.data.mapper.decorator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.enums.EventType;
import project.task.manager.user_service.data.event.UserUpdatedEvent;
import project.task.manager.user_service.data.mapper.OutboxMapper;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.kafka.properties.KafkaTopicProperties;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@Primary
@Setter
public abstract class OutboxMapperDecorator implements OutboxMapper {
	
	@Autowired
	@Qualifier("delegate")
	private OutboxMapper delegate;
	@Autowired
	private KafkaTopicProperties kafkaTopicProperties;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public Outbox mapUpdateToOutbox(User user, UserUpdateDto update) {
		try {
			Outbox outbox = delegate.mapUpdateToOutbox(user, update);
			
			UserUpdatedEvent event = mapUpdateDtoToEvent(user, update);
			
			JsonNode node = objectMapper.convertValue(event, JsonNode.class);
			outbox.setAggregateId(user.getId());
			outbox.setPayload(node);
			outbox.setEventType(EventType.USER_UPDATED);
			
			return outbox;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private UserUpdatedEvent mapUpdateDtoToEvent(User user, UserUpdateDto update) {
		if(update != null && user != null) {
			return UserUpdatedEvent.builder()
					.userId(user.getId())
					.name(update.name())
					.lastName(update.lastName())
					.birthDate(update.birthDate())
					.phone(update.phone())
					.eventType(EventType.USER_UPDATED)
					.build();
		}
		log.error("User or update is null");
		throw new RuntimeException("User or update is null");
	}
}