package project.task.manager.user_service.data.mapper.decorator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.enums.EventType;
import project.task.manager.user_service.data.mapper.OutboxMapper;
import project.task.manager.user_service.data.request.EventHeaderData;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.properties.KafkaProperties;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Component
@Primary
@Setter
public abstract class OutboxMapperDecorator implements OutboxMapper {
	
	@Autowired
	@Qualifier("delegate")
	private OutboxMapper delegate;
	@Autowired
	private KafkaProperties kafkaProperties;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public Outbox mapUpdateToOutbox(User user, UserUpdateDto update) {
		Outbox outbox = delegate.mapUpdateToOutbox(user, update);
		
		EventHeaderData header = new EventHeaderData(kafkaProperties.getMessageLifetime(), user.getId());
		outbox.setPayload(objectMapper.convertValue(update, JsonNode.class));
		outbox.setHeader(objectMapper.convertValue(header, JsonNode.class));
		outbox.setEventType(EventType.USER_UPDATED);
		
		return outbox;
	}
}