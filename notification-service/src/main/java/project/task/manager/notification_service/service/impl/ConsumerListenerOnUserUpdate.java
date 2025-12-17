package project.task.manager.notification_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.event.UserUpdatedEvent;
import project.task.manager.notification_service.data.mapper.InboxMapper;
import project.task.manager.notification_service.data.repository.InboxRepository;
import project.task.manager.notification_service.service.ConsumerListener;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerListenerOnUserUpdate implements ConsumerListener<UserUpdatedEvent> {
	
	private final SendMailService sendMailService;
	private final InboxRepository inboxRepository;
	private final InboxMapper inboxMapper;
	
	@Override
	@KafkaHandler
	@KafkaListener(
			topics = "${properties.kafka.userUpdated.topic}",
			groupId = "${properties.kafka.userUpdated.group}")
	public void consume(UserUpdatedEvent event) {
		Inbox result = inboxRepository.save(inboxMapper.mapUpdateEventToInbox(event));
		
		log.debug("Saved inbox: {}", result.getId() + "");
	}
	
	@Override
	public void publish(UserUpdatedEvent event) {
		sendMailService.send(event);
	}
}