package project.task.manager.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import project.task.manager.notification_service.data.event.UserUpdatedEvent;

/**
 * @author 4ndr33w
 * @version 1.0
 */
//@Service
//@RequiredArgsConstructor
public class KafkaConsumerOnUpdate {
	
//	@KafkaHandler
//	@KafkaListener(
//			topics = "${properties.kafka.userUpdated.topic}",
//			groupId = "${properties.kafka.userUpdated.group}")
//	public void consume(UserUpdatedEvent event) {
//
//		var test = event;
//		String test1 = test.toString();
//	}
}