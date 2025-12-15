package project.task.manager.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.task.manager.notification_service.data.dto.abstraction.Event;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
	
	public void send(Event event) {
		log.info("Sending email to {} with subject {}", event.getEmail(), event.getUserId());
	}
}
