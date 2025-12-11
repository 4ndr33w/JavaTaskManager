package project.task.manager.project_service.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
