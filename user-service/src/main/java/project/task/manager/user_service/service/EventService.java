package project.task.manager.user_service.service;

import project.task.manager.user_service.data.entity.Outbox;

import java.util.List;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface EventService {
	
	void sendEvent(Outbox outbox);
	
	void sendEvents(List<Outbox> outboxes);
}