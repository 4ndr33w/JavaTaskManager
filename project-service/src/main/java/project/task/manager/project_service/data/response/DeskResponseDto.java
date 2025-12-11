package project.task.manager.project_service.data.response;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record DeskResponseDto(
		UUID id,
		ZonedDateTime created,
		ZonedDateTime updated,
		String deskName,
		String description,
		boolean privacy,
		String[] columns,
		UserDto admin,
		UUID projectId,
		List<TaskResponseDto> tasks
) {
}
