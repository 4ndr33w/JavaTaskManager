package project.task.manager.notification_service.data.response;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record ShortUserResponseDto(
		UUID id,
		String name,
		String lastName,
		String userName,
		String email) {
}