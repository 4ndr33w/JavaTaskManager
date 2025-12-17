package project.task.manager.notification_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.event.UserUpdatedEvent;
import project.task.manager.notification_service.data.event.abstraction.Event;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;
import project.task.manager.notification_service.properties.NotificationSenderProperties;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Utils {
	
	private final ObjectMapper objectMapper;
	private final NotificationSenderProperties senderProperties;
	
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
	
	public SimpleMailMessage buildMessage(Inbox event, ShortUserResponseDto user) {
		Event payload = getEventPayload(event);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderProperties.getEmailSender());
		message.setTo(user.email());
		message.setSubject("Task manager notification");
		message.setText(getEmailMessageText(payload, user));
		
		return message;
	}
	
	private String getEmailMessageText(Event event, ShortUserResponseDto user) {
		if(event instanceof UserUpdatedEvent) {
			String name = ((UserUpdatedEvent) event).getName() == null? user.name() : ((UserUpdatedEvent) event).getName();
			String lastName = ((UserUpdatedEvent) event).getLastName() == null? user.lastName() : ((UserUpdatedEvent) event).getLastName();
			UserUpdatedEvent updatedEvent = (UserUpdatedEvent) event;
			
			StringBuilder sb = new StringBuilder();
			sb.append("Здравствуйте, ").append(name).append(" ").append(lastName).append("!").append("\n");
			sb.append("Бла-бла-бла!\nПрофиль обновлён:").append("\n");
			if(updatedEvent.getName() != null) {
				sb.append("Новое имя: ").append(updatedEvent.getName()).append("\n");
			}
			if(updatedEvent.getLastName() != null) {
				sb.append("Обновлённая фамилия: ").append(updatedEvent.getLastName()).append("\n");
			}
			if(updatedEvent.getPhone() != null) {
				sb.append("Обновлённый номер телефона: ").append(updatedEvent.getPhone()).append("\n");
			}
			if(updatedEvent.getBirthDate() != null) {
				sb.append("Обновлённая дата рождения: ").append(updatedEvent.getBirthDate()).append("\n");
			}
			
			return sb.toString();
		} else {
			return event.toString();
		}
	}
}