package project.task.manager.notification_service.service.abstraction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.task.manager.notification_service.data.dto.abstraction.Event;
import project.task.manager.notification_service.service.EmailService;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class ConsumerListener<E extends Event> {
	private final EmailService emailService;
	
	public abstract void consume(E event);
	
	/**
	 * ToDo: создать логику обработки и публикации событий
	 * @param event
	 */
	public void dispatch(Event event) {
		log.info("Получено событие: {}", event);
		
		emailService.send(event);
	}
}