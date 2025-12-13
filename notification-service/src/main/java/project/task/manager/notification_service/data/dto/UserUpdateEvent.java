package project.task.manager.notification_service.data.dto;

import project.task.manager.notification_service.data.enums.EventType;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record UserUpdateEvent(
		
		UUID userId,
		EventType eventType,
		String email,
		String password,
		String name,
		String lastName) {}