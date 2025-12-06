package project.task.manager.user_service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaProperties {
	
	private String userCreated;
	private String userUpdated;
	private Long messageLifetime;
}