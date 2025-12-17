package project.task.manager.notification_service.client.configuration;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Конфигурация RateLimiter: ограничение количества запросов к REST-клиенту
 * в течение заданного интервала времени
 *
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
public class RateLimiterConfiguration {
	
	@Bean
	public RateLimiterConfig defaultRateLimiterConfig() {
		return RateLimiterConfig.custom()
				.limitForPeriod(10)
				.limitRefreshPeriod(Duration.ofSeconds(1))
				.timeoutDuration(Duration.ofMillis(600))
				.build();
	}
	
	@Bean
	public RateLimiterRegistry rateLimiterRegistry() {
		RateLimiterRegistry registry = RateLimiterRegistry.ofDefaults();
		registry.addConfiguration("defaultRateLimiter", defaultRateLimiterConfig());
		return registry;
	}
}