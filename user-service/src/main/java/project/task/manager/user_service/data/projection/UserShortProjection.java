package project.task.manager.user_service.data.projection;

import java.util.UUID;

/**
 * Проекция для краткого представления пользователя
 *
 * @author 4ndr33w
 * @version 1.0
 */
public interface UserShortProjection {
	
	UUID getId();
	String getName();
	String getLastName();
	String getUserName();
	String getEmail();
}