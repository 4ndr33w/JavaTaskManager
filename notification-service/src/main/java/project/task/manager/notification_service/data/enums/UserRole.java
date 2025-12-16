package project.task.manager.notification_service.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
	GUEST("GUEST"),
	USER("USER"),
	ADMIN("ADMIN");
	
	private final String value;
	
}
