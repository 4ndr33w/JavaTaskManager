package project.task.manager.notification_service.service;

import project.task.manager.notification_service.data.event.abstraction.Event;

/**
 * @author 4ndr33w
 * @version 1.0
 */

public interface ConsumerListener<E extends Event> {
	
	void consume(E event);
	
	void publish(E event);
}
