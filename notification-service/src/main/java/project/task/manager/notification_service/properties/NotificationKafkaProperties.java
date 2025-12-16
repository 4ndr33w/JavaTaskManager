package project.task.manager.notification_service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import project.task.manager.notification_service.properties.data.ConsumerProperties;
import project.task.manager.notification_service.properties.data.NotificationTypeMapping;

import java.util.List;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "properties.kafka")
public class NotificationKafkaProperties {
	private List<NotificationTypeMapping> notificationTypeMappingList;
	private ConsumerProperties consumer;
	private String headerTypeName;
}