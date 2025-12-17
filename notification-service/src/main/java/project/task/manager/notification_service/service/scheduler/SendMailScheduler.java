package project.task.manager.notification_service.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.task.manager.notification_service.client.UserServiceClient;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.enums.EventStatus;
import project.task.manager.notification_service.data.event.abstraction.Event;
import project.task.manager.notification_service.data.repository.InboxRepository;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;
import project.task.manager.notification_service.service.SendService;
import project.task.manager.notification_service.util.Utils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailScheduler {
	
	private final UserServiceClient userServiceClient;
	private final InboxRepository inboxRepository;
	private final Utils utils;
	private final SendService sendService;
	
	@Async("asyncExecutor")
	@Scheduled(cron = "${scheduler.inbox.cron}")
	public void sendMail() {
		List<Inbox> events= inboxRepository.findAllByStatus(EventStatus.RECEIVED);
		
		List<UUID> ids = events.stream()
				.map(utils::getEventPayload)
				.map(Event::getUserId)
				.toList();
				
		ResponseEntity<List<ShortUserResponseDto>> responseEntity = userServiceClient.getUsersByIds(ids);
		
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			List<ShortUserResponseDto> users = responseEntity.getBody();
			
			if(!events.isEmpty() && !users.isEmpty()){
				events.forEach(event -> {
					Event payload = utils.getEventPayload(event);
					Optional<ShortUserResponseDto> userOptional = users.stream().filter(u -> u.id().equals(payload.getUserId())).findFirst();
					userOptional.ifPresent(shortUserResponseDto -> sendService.send(event, shortUserResponseDto));
				});
			}
		}
	}
}