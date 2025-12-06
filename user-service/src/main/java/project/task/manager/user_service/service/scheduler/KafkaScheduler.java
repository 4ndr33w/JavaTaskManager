package project.task.manager.user_service.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.data.repository.OutboxRepository;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaScheduler {
	
	private final OutboxRepository outboxRepository;
	
	@Async("asyncExecutor")
	@Scheduled(cron = "scheduler.kafka.cron")
	public void publishKafkaMessage() {
	
	}
}