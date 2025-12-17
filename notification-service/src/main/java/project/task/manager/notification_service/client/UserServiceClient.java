package project.task.manager.notification_service.client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;
import project.task.manager.notification_service.exception.ServiceUnavailableException;
import project.task.manager.notification_service.exception.TooManyRequestsException;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@FeignClient(name = "${users.client.name}", url = "${users.client.host}")
public interface UserServiceClient {
	
	/**
	 * Получение краткой информации о пользователе по его идентификатору
	 * REST-запросом в user-service
	 *
	 * @param userId уникальный идентификатор пользователя
	 * @return краткая информация о пользователе
	 */
	@CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallbackGetUserByIdCircuitBreaker")
	@Retry(name = "defaultRetry", fallbackMethod = "fallbackGetUserByIdRetry")
	@RateLimiter(name = "defaultRateLimiter", fallbackMethod = "fallbackGetUserByIdRateLimiter")
	@GetMapping("/user-service/api/v1/users/short/{userId}")
	ResponseEntity<ShortUserResponseDto> getUserById(@PathVariable UUID userId);
	
	/**
	 * Получение списка пользователей по их идентификаторам
	 * REST-запросом в user-service
	 *
	 * @param userIds список уникальных идентификаторов пользователей
	 * @return список краткой информации о пользователях
	 */
	@CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallbackGetUserByIdCircuitBreaker")
	@Retry(name = "defaultRetry", fallbackMethod = "fallbackGetUsersByIdsRetry")
	@RateLimiter(name = "defaultRateLimiter", fallbackMethod = "fallbackGetUsersByIdsRateLimiter")
	@PostMapping("/user-service/api/v1/users/short/list")
	ResponseEntity<List<ShortUserResponseDto>> getUsersByIds(@RequestBody List<UUID> userIds);
	
	default ResponseEntity<ShortUserResponseDto> fallbackGetUserByIdCircuitBreaker(UUID userId, CallNotPermittedException e) {
		throw new ServiceUnavailableException("Сработал Circuit Breaker: %s; %s".formatted(e.getCausingCircuitBreakerName(), e.getMessage()));
	}
	
	default ResponseEntity<List<ShortUserResponseDto>> fallbackGetUsersByIdsCircuitBreaker(List<UUID> userIds, CallNotPermittedException e) {
		throw new ServiceUnavailableException("Сработал Circuit Breaker: %s; %s".formatted(e.getCausingCircuitBreakerName(), e.getMessage()));
	}
	
	default ResponseEntity<ShortUserResponseDto> fallbackGetUserByIdRetry(UUID userId, Exception e) {
		throw new ServiceUnavailableException("Сработал Retry-fallback: %s".formatted(e.getMessage()));
	}
	
	default ResponseEntity<List<ShortUserResponseDto>> fallbackGetUsersByIdsRetry(List<UUID> userIds, Exception e) {
		throw new ServiceUnavailableException("Сработал Retry-fallback: %s".formatted(e.getMessage()));
	}
	
	default ResponseEntity<ShortUserResponseDto> fallbackGetUserByIdRateLimiter(UUID userId, Exception e) {
		throw new TooManyRequestsException("Сработал RateLimiter-fallback: %s".formatted(e.getMessage()));
	}
	
	default ResponseEntity<List<ShortUserResponseDto>> fallbackGetUsersByIdsRateLimiter(List<UUID> userIds, Exception e) {
		throw new TooManyRequestsException("Сработал RateLimiter-fallback: %s".formatted(e.getMessage()));
	}
}