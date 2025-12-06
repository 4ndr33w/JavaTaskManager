package project.task.manager.user_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Сначала {@link BCryptPasswordEncoder} был в @{link SecurityConfig}
 * Но это вызвало циклическую зависимость
 * По этому я вынес его в отдельный класс
 *
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
public class PasswordEncoderConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
