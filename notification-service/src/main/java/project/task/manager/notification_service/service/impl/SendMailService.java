package project.task.manager.notification_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.enums.EventStatus;
import project.task.manager.notification_service.data.repository.InboxRepository;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;
import project.task.manager.notification_service.service.SendService;
import project.task.manager.notification_service.util.Utils;

import java.time.ZonedDateTime;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SendMailService implements SendService {
	
	private final JavaMailSender javaMailSender;
	private final InboxRepository inboxRepository;
	private final Utils utils;
	
	@Override
	@Transactional
	public void send(Inbox event, ShortUserResponseDto user) {
		SimpleMailMessage message = utils.buildMessage(event, user);
		try {
			javaMailSender.send(message);
			event.setStatus(EventStatus.SENT);
			event.setProcessedAt(ZonedDateTime.now());
			inboxRepository.save(event);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}