package project.task.manager.notification_service.data.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import project.task.manager.notification_service.data.event.abstraction.Event;

import java.io.Serializable;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreatedEvent extends Event implements Serializable {
}
