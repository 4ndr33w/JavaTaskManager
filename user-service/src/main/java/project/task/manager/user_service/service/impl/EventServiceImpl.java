package project.task.manager.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.enums.EventStatus;
import project.task.manager.user_service.data.event.BaseEvent;
import project.task.manager.user_service.data.event.EventHeaderData;
import project.task.manager.user_service.data.repository.OutboxRepository;
import project.task.manager.user_service.kafka.serializer.KafkaSerializer;
import project.task.manager.user_service.service.EventService;
import project.task.manager.user_service.util.Utils;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
	
	private final OutboxRepository outboxRepository;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final KafkaSerializer serializer;
	private final Utils utils;
	
	@Override
	@Transactional
	public void sendEvent(Outbox outbox) {
		Map<String, Object> headersMap = mapHeaders(outbox);
		BaseEvent payload = utils.getEventPayload(outbox);
		String topic = utils.getTopicName(payload.getEventType());
		String key = String.valueOf(payload.getUserId());
		
		Message<byte[]> message = serializer.serialize(topic, key, payload, headersMap);
		
		kafkaTemplate.send(message);
		processSentEvents(outbox);
		outboxRepository.save(outbox);
	}
	
	private void processSentEvents(Outbox outbox) {
		outbox.setStatus(EventStatus.SENT);
		outbox.setRetryCount(outbox.getRetryCount() + 1);
		outbox.setSentAt(ZonedDateTime.now());
	}
	
	private Map<String, Object> mapHeaders(Outbox outbox) {
		EventHeaderData headers = utils.getEventHeaderData(outbox);
		Map<String, Object> map = new HashMap<>();
		
		map.put("messageId", headers.messageId());
		map.put("eventType", headers.eventType());
		map.put("messageLifetime", headers.messageLifetime());
		map.put("employeeId", headers.employeeId());
		map.put("retryCount", headers.retryCount());
		map.put("createdAt", headers.createdAt());
		
		return map;
	}
	
	@Override
	public void sendEvents(List<Outbox> outboxes) {
	
	}
}