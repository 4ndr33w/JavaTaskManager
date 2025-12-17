package project.task.manager.notification_service.data.mapper.decorator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.event.UserUpdatedEvent;
import project.task.manager.notification_service.data.mapper.InboxMapper;
import project.task.manager.notification_service.exception.MappingException;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@Primary
@Setter
public abstract class InboxMapperDecorator implements InboxMapper {
	
	@Autowired
	@Qualifier("delegate")
	private InboxMapper delegate;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public Inbox mapUpdateEventToInbox(UserUpdatedEvent event) {
		try {
			Inbox inbox = delegate.mapUpdateEventToInbox(event);
			JsonNode node = objectMapper.convertValue(event, JsonNode.class);
			inbox.setPayload(node);
			
			return inbox;
		}
		catch (Exception e) {
			log.error("Ошибка при маппинге в Inbox", e);
			throw new MappingException("Ошибка при маппинге в Inbox", e);
		}
	}
}