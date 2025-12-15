package project.task.manager.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import project.task.manager.notification_service.data.dto.UserUpdateEvent;
import project.task.manager.notification_service.service.abstraction.ConsumerListener;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Service
public class ConsumerOnUpdate extends ConsumerListener<UserUpdateEvent> {
	
	public ConsumerOnUpdate(EmailService emailService) {
		super(emailService);
	}
	
	@Override
	@KafkaHandler
	@KafkaListener(topics = "${kafka.topics.userUpdated.name}", groupId = "${kafka.topics.userUpdated.group}")
	public void consume(UserUpdateEvent event) {
		dispatch(event);
	}
}