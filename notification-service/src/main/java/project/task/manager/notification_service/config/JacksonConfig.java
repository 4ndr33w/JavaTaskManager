package project.task.manager.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
public class JacksonConfig {
	
	@Bean
	public ObjectMapper objectMapper() {
		com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}