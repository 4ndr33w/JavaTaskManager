package project.task.manager.user_service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс конфигурации свойств CORS (Cross-Origin Resource Sharing).
 * <p>
 * Загружает параметры политики CORS из конфигурационных файлов приложения с префиксом {@code cors}.
 * Позволяет конфигурировать разрешённые источники, методы, заголовки, а также настройки
 * для передачи учётных данных и времени кеширования preflight-запросов.
 *
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
	
	/**
	 * Список допустимых-Origin (источников) для CORS-запросов.
	 * Например, {@code http://localhost:3000}, {@code https://mydomain.com}.
	 */
	private List<String> allowedOriginPatterns;
	
	/**
	 * Список допустимых HTTP-методов для CORS-запросов.
	 * Например, {@code GET}, {@code POST}, {@code PUT}, {@code DELETE}.
	 */
	private List<String> allowedMethods;
	
	/**
	 * Список допустимых заголовков, которые могут быть использованы клиентом в CORS-запросах.
	 * Например, {@code Content-Type}, {@code Authorization}.
	 */
	private List<String> allowedHeaders;
	
	/**
	 * Список заголовков, которые должны быть доступны клиенту в ответе (exposed headers).
	 */
	private List<String> exposedHeaders;
	
	/**
	 * Указывает, разрешена ли передача учётных данных (таких как cookies, авторизационные заголовки)
	 * при CORS-запросах.
	 */
	private boolean allowCredentials;
	
	/**
	 * Время (в секундах), на которое можно кешировать preflight-запросы (OPTIONS).
	 */
	private long maxAge;
}
