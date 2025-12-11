package project.task.manager.project_service.data.request;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record DeskRequestDto(
		String deskName,
		String description,
		boolean privacy,
		String[] columns,
		UUID adminId,
		UUID projectId
) {
}
