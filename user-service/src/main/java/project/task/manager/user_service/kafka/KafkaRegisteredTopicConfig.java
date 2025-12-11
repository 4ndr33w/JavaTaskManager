package project.task.manager.user_service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.task.manager.user_service.kafka.properties.KafkaTopicProperties;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafkaRegisteredTopicConfig {
	
	private final KafkaTopicProperties kafkaTopicProperties;
	
	@Bean
	public NewTopic userCreatedTopic() {
		return new NewTopic(
				kafkaTopicProperties.topics().userCreated().name(),
				kafkaTopicProperties.topics().userCreated().partitions(),
				kafkaTopicProperties.topics().userCreated().replicationFactor());
	}
	
	@Bean
	public NewTopic userUpdatedTopic() {
		return new NewTopic(
				kafkaTopicProperties.topics().userUpdated().name(),
				kafkaTopicProperties.topics().userUpdated().partitions(),
				kafkaTopicProperties.topics().userUpdated().replicationFactor());
	}
	
	@Bean
	public NewTopic userDeletedTopic() {
		return new NewTopic(
				kafkaTopicProperties.topics().userDeleted().name(),
				kafkaTopicProperties.topics().userDeleted().partitions(),
				kafkaTopicProperties.topics().userDeleted().replicationFactor());
	}
}