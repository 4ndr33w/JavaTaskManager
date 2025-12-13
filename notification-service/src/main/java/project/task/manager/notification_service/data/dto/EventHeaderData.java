package project.task.manager.notification_service.data.dto;

import project.task.manager.notification_service.data.enums.EventType;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record EventHeaderData(
		UUID messageId,
		EventType eventType,
		Long messageLifetime,
		UUID employeeId,
		int retryCount,
		ZonedDateTime createdAt) {}
