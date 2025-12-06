package project.task.manager.user_service.exception;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public class TokenExpirationException extends RuntimeException {

		public TokenExpirationException(String message) {
				super(message);
		}
		public TokenExpirationException(String message, Throwable cause) {
				super(message, cause);
		}
}