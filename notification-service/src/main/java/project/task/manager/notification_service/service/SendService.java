package project.task.manager.notification_service.service;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface SendService<E> {
	
	void send(E event);
}
