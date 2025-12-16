package project.task.manager.notification_service.data.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import project.task.manager.notification_service.data.enums.EventType;
import project.task.manager.notification_service.data.event.abstraction.Event;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdatedEvent extends Event implements Serializable {
	
	private UUID messageId;
	private UUID userId;
	private EventType eventType;
	private String name;
	private String lastName;
	private LocalDate birthDate;
	private String phone;
	private int retryCount;
	private ZonedDateTime createdAt;
}