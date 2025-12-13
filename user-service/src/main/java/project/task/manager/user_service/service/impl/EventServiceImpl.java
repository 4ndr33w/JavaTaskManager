package project.task.manager.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.enums.EventStatus;
import project.task.manager.user_service.data.event.BaseEvent;
import project.task.manager.user_service.data.repository.OutboxRepository;
import project.task.manager.user_service.service.EventService;
import project.task.manager.user_service.util.Utils;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
	
	private final OutboxRepository outboxRepository;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final Utils utils;
	
	@Override
	@Transactional
	public void sendEvent(Outbox outbox) {
		BaseEvent payload = utils.getEventPayload(outbox);
		String topic = utils.getTopicName(payload.getEventType());
		String key = String.valueOf(payload.getUserId());

		kafkaTemplate.send(topic, key, payload);
		processSentEvents(outbox);
		outboxRepository.save(outbox);
	}
	
	private void processSentEvents(Outbox outbox) {
		outbox.setStatus(EventStatus.SENT);
		outbox.setRetryCount(outbox.getRetryCount() + 1);
		outbox.setSentAt(ZonedDateTime.now());
	}
	
	@Override
	public void sendEvents(List<Outbox> outboxes) {
	
	}
}