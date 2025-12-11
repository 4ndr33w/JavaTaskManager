package project.task.manager.project_service.data.response;

import project.task.manager.project_service.data.enums.ProjectStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record ProjectResponseDto(
		UUID id,
		ZonedDateTime created,
		ZonedDateTime updated,
		String projectName,
		byte[] image,
		String description,
		UserDto admin,
		ProjectStatus projectStatus,
		List<UserDto> users,
		List<DeskResponseDto> desks
) {
}
