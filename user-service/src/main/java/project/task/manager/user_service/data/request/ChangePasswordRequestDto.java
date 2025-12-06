package project.task.manager.user_service.data.request;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record ChangePasswordRequestDto(
				String oldPassword,
				String newPassword
) {
}
