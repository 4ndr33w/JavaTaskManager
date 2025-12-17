package project.task.manager.notification_service.client.configuration;

import feign.FeignException;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * Конфигурация Retry: Пытаться получить ответ от REST-клиента заданное количество раз
 * с заданным интервалом
 * при неудачных попытках получить ответ
 *
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
public class RetryConfiguration {
	
	@Bean
	public RetryConfig defaultRetryConfig() {
		return RetryConfig.custom()
				.maxAttempts(3)
				.waitDuration(Duration.ofMillis(500))
				.retryExceptions(IOException.class, TimeoutException.class, FeignException.class)
				.build();
	}
	
	@Bean
	public RetryRegistry retryRegistry() {
		RetryRegistry registry = RetryRegistry.ofDefaults();
		registry.addConfiguration("defaultRetry", defaultRetryConfig());
		return registry;
	}
}