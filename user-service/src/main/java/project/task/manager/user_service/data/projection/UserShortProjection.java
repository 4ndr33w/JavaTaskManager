package project.task.manager.user_service.data.projection;

/**
 * Проекция для краткого представления пользователя
 *
 * @author 4ndr33w
 * @version 1.0
 */
public interface UserShortProjection {
	
	String getName();
	String getLastName();
	String getUserName();
	String getEmail();
}