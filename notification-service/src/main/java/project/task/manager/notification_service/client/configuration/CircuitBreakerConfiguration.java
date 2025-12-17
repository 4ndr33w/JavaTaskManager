package project.task.manager.notification_service.client.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Конфигурация CircuitBreaker - отключение запросов к сервису
 * при преодолении заданного процента неудачных запросов к REST-клиенту
 *
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
public class CircuitBreakerConfiguration {
	
	@Bean
	public CircuitBreakerConfig defaultCircuitBreakerConfig() {
		return CircuitBreakerConfig.custom()
				.failureRateThreshold(50)
				.waitDurationInOpenState(Duration.ofSeconds(30))
				.slidingWindowSize(10)
				.minimumNumberOfCalls(5)
				.build();
	}
	
	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
		registry.addConfiguration("defaultCircuitBreaker", defaultCircuitBreakerConfig());
		return registry;
	}
}