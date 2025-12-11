package project.task.manager.project_service.data.request;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record ProjectRequestDto(
		String projectName,
		String description,
		UUID adminId,
		List<UUID> userIds
) {
}
