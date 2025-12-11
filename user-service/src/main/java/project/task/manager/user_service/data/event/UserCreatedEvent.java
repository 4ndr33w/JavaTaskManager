package project.task.manager.user_service.data.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.task.manager.user_service.data.enums.EventType;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent implements BaseEvent {

	private UUID userId;
	private EventType eventType;
	private String email;
	private String password;
	private String name;
	private String lastName;
}