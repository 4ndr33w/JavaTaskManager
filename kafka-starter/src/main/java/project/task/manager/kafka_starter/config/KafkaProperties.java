package project.task.manager.kafka_starter.config;

import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import project.task.manager.kafka_starter.config.consumer.Consumer;
import project.task.manager.kafka_starter.config.producer.Producer;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Builder
@ConfigurationProperties(prefix = "properties.kafka")
public class KafkaProperties {
	private String bootstrapServers;
	private String avroSchemaPath;
	private Producer producer;
	private Consumer consumer;
}
