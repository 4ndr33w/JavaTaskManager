package project.task.manager.user_service.data.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatedEvent implements BaseEvent {
	
	private UUID eventId;
	private UUID userId;
	private String login;
	private Instant updatedAt;
}
