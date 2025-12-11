package project.task.manager.project_service.properties;

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
@ConfigurationProperties(prefix = "users.client")
public class UserServiceClientProperties {
	private String host;
	private String name;
}
