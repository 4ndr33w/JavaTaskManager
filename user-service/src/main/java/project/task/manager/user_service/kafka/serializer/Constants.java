package project.task.manager.user_service.kafka.serializer;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public final class Constants {
	
	public static final String OBJECT_TYPE_HEADER = "X-Object-Type";
	public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
	public static final String MESSAGE_VERSION_HEADER = "X-Message-Version";
	
	private Constants() {
		throw new AssertionError("Utility class should not be instantiated");
	}
}
