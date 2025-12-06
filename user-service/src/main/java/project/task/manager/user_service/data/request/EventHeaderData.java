package project.task.manager.user_service.data.request;

import java.util.UUID;

/**
 * Заголовокивента кафки
 * @author 4ndr33w
 * @version 1.0
 */
public record EventHeaderData(
		Long messageLifetime,
		UUID employeeId
) {}
