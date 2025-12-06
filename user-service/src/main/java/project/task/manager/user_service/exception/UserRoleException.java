package project.task.manager.user_service.exception;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public class UserRoleException extends RuntimeException {
		public UserRoleException(String message) {
				super(message);
		}

		public UserRoleException(String message,Throwable cause) {
				super(message, cause);
		}
}
