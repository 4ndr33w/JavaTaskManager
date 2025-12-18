package project.task.manager.user_service.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.task.manager.user_service.properties.CorsProperties;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class CorsConfig {
	
	private final CorsProperties corsProperties;
	
	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOriginPatterns(corsProperties.getAllowedOriginPatterns());
		config.setAllowedHeaders(corsProperties.getAllowedHeaders());
		config.setAllowedMethods(corsProperties.getAllowedMethods());
		config.setAllowCredentials(corsProperties.isAllowCredentials());
		config.setMaxAge(corsProperties.getMaxAge());
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
}