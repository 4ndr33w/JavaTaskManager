package project.task.manager.user_service.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.enums.EventStatus;
import project.task.manager.user_service.data.repository.OutboxRepository;
import project.task.manager.user_service.service.EventService;

import java.util.List;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaScheduler {
	
	private final OutboxRepository outboxRepository;
	private final EventService eventService;
	
	@Async("asyncExecutor")
	@Scheduled(fixedRateString = "${outbox.sender.fixed-rate-ms:10000}")
	@SchedulerLock(
			name = "KafkaScheduler.publishKafkaMessage",
			lockAtMostFor = "${outbox.sender.lock-at-most-for:9s}",
			lockAtLeastFor = "${outbox.sender.lock-at-least-for:1s}"
	)
	public void publishKafkaMessage() {
		log.info("KafkaScheduler: publishKafkaMessage");
		
		List<Outbox> events = outboxRepository.findAllByStatus(EventStatus.CREATED);
		
		if(!events.isEmpty()) {
			events.forEach(eventService::sendEvent);
		}
	}
}