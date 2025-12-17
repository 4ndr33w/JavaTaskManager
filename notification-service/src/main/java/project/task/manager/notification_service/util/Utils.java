package project.task.manager.notification_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.event.UserUpdatedEvent;
import project.task.manager.notification_service.data.event.abstraction.Event;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Utils {
	
	private final ObjectMapper objectMapper;
	
	public Event getEventPayload(Inbox inbox) {
		try {
			var payload = inbox.getPayload().toString();
			UserUpdatedEvent event = objectMapper.readValue(payload, UserUpdatedEvent.class);
			event.setMessageId(inbox.getId());
			return event;
		} catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
