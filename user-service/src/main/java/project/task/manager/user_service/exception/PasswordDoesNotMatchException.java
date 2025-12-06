package project.task.manager.user_service.exception;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public class PasswordDoesNotMatchException extends RuntimeException {
		public PasswordDoesNotMatchException(String message) {
				super(message);
		}
		public PasswordDoesNotMatchException(String message, Throwable cause) {
				super(message, cause);
		}
}
