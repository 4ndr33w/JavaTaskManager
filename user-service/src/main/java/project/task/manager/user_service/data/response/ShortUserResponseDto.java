package project.task.manager.user_service.data.response;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record ShortUserResponseDto(
		String name,
		String lastName,
		String userName,
		String email) {}