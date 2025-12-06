package project.task.manager.user_service.exception;

public class SecurityContextHolderException extends RuntimeException {
    public SecurityContextHolderException(String message) {
        super(message);
    }

		public SecurityContextHolderException(String message, Throwable cause) {
				super(message, cause);
		}
}
