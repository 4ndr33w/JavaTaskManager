package project.task.manager.notification_service.exception;

public class KafkaDeserializationException extends RuntimeException {
	public KafkaDeserializationException(String message) {
		super(message);
	}
	public KafkaDeserializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
