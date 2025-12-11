package project.task.manager.project_service.data.response;

import project.task.manager.project_service.data.enums.TaskPriority;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record TaskResponseDto(
		UUID id,
		ZonedDateTime created,
		ZonedDateTime updated,
		String taskName,
		byte[] image,
		ZonedDateTime startTime,
		ZonedDateTime endTime,
		byte[] file,
		TaskPriority priority,
		String fileName,
		String column,
		UUID deskId,
		UserDto creator,
		UserDto executor
) {
}
