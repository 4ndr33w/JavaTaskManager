package project.task.manager.notification_service.service;

import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface SendService {
	
	void send(Inbox event, ShortUserResponseDto user);
}
