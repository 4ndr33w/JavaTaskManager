package project.task.manager.user_service.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@ConfigurationProperties(prefix = "kafka")
public record KafkaTopicProperties(Topics topics, Long messageLifetime) {
	
	public record Topics(
			TopicConfig userCreated,
			TopicConfig userUpdated,
			TopicConfig userDeleted
	) {}
	
	public record TopicConfig(
			String name,
			int partitions,
			short replicationFactor
	) {}
}