package project.task.manager.user_service.data.event;

import project.task.manager.user_service.data.enums.EventType;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Заголовокивента кафки
 * @author 4ndr33w
 * @version 1.0
 */
public record EventHeaderData(
		UUID messageId,
		EventType eventType,
		Long messageLifetime,
		UUID employeeId,
		int retryCount,
		ZonedDateTime createdAt
) {}