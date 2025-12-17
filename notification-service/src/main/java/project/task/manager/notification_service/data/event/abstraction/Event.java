package project.task.manager.notification_service.data.event.abstraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.task.manager.notification_service.data.enums.EventType;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {
	
	private UUID messageId;
	private UUID userId;
	private EventType eventType;
}
