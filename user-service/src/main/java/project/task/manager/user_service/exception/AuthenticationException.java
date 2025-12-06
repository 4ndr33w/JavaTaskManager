package project.task.manager.user_service.exception;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public class AuthenticationException extends RuntimeException {
		public AuthenticationException(String message) {
				super(message);
		}

		public AuthenticationException(String message, Throwable cause) {
				super(message, cause);
		}
}
