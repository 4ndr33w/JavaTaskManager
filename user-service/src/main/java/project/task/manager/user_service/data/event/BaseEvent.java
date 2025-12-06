package project.task.manager.user_service.data.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = UserCreatedEvent.class, name = "UserCreatedEvent"),
		@JsonSubTypes.Type(value = UserUpdatedEvent.class, name = "UserUpdatedEvent")
})
public interface BaseEvent {
	
	UUID getEventId();
	void setEventId(UUID eventId);
	UUID getUserId();
	String getLogin();
}
