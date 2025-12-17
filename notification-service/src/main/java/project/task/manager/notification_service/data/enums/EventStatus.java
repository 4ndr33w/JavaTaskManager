package project.task.manager.notification_service.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum EventStatus {
	
	RECEIVED("RECEIVED"),
	SENT("SENT");
	
	private final String value;
}