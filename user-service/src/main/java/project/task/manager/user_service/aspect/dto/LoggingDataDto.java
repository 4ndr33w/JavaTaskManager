package project.task.manager.user_service.aspect.dto;

/**
 * @author 4ndr33w
 * @version 1.0
 *
 * Объект для хранения данных об объекте логирования
 */
public record LoggingDataDto(
				String className,
				String methodName,
				Object[] args,
				long startTime
) {
}
