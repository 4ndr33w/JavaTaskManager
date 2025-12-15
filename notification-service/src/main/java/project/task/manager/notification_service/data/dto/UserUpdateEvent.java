package project.task.manager.notification_service.data.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import project.task.manager.notification_service.data.dto.abstraction.Event;
import project.task.manager.notification_service.data.enums.EventType;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateEvent extends Event {
	
	private UUID messageId;
	UUID userId;
	EventType eventType;
	String name;
	String lastName;
	LocalDate birthDate;
	String phone;
	int retryCount;
	ZonedDateTime createdAt;
}