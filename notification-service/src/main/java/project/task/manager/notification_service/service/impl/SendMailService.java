package project.task.manager.notification_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.task.manager.notification_service.client.UserServiceClient;
import project.task.manager.notification_service.data.event.abstraction.Event;
import project.task.manager.notification_service.data.event.request.UserDto;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;
import project.task.manager.notification_service.properties.NotificationSenderProperties;
import project.task.manager.notification_service.service.SendService;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SendMailService implements SendService<Event> {
	
	private final JavaMailSender javaMailSender;
	private final NotificationSenderProperties senderProperties;
	private final UserServiceClient userServiceClient;
	
	@Override
	public void send(Event event) {
		
		ShortUserResponseDto user = userServiceClient.getUserById(event.getUserId()).getBody();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderProperties.getEmailSender());
		message.setTo(user.email());
		message.setSubject("Task manager notification");
		message.setText(event.toString());
		
		try {
			javaMailSender.send(message);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	
	}
}