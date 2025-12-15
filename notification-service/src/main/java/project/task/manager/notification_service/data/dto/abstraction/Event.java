package project.task.manager.notification_service.data.dto.abstraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.task.manager.notification_service.data.enums.EventType;

import java.util.UUID;

/**
 * Абстрактный базовый класс для всех событий в системе.
 * Содержит общие поля и методы для всех типов событий.
 *
 * @author 4ndr33w
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {
	
	/**
	 * Уникальный идентификатор события
	 */
	UUID messageId;
	
	/**
	 * Уникальный идентификатор пользователя
	 */
	UUID userId;
	
	/**
	 * Тип события
	 */
	EventType eventType;
	
	/**
	 * Email пользователя
	 */
	String email;
}