package project.task.manager.user_service.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum EventType {
	
	USER_CREATED("user created"),
	USER_DELETED("user deleted"),
	USER_UPDATED("user updated");
	
	private final String value;
}
