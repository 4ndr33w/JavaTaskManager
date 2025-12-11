package project.task.manager.project_service.data.request;

import project.task.manager.project_service.data.enums.TaskPriority;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record TaskRequestDto(
		String taskName,
		ZonedDateTime startTime,
		ZonedDateTime endTime,
		TaskPriority priority,
		String column,
		UUID deskId,
		UUID executorId
) {
}
