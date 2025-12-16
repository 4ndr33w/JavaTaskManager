package project.task.manager.notification_service.exception;

public class LoadClassException extends RuntimeException {
	public LoadClassException(String message) {
		super(message);
	}
	public LoadClassException(String message, Throwable cause) {
		super(message, cause);
	}
}
