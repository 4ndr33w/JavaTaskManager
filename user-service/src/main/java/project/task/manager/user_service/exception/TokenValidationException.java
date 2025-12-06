package project.task.manager.user_service.exception;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public class TokenValidationException extends RuntimeException {
		public TokenValidationException(String message) {
				super(message);
		}

		public TokenValidationException(String message, Throwable cause) {
				super(message, cause);
		}
}
