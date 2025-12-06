package project.task.manager.kafka_starter.config.producer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "properties.kafka.producer")
public class Producer {
	private String keySerializer;
	private String valueSerializer;
	private String topic;
	private boolean idempotenceEnabled ; // Идемпотентность для надежности
	private int acks; // Подтверждения: 0, 1, -1 (all)
	private int retries; // Количество попыток при сбоях
	private int retryBackoffMs; // Задержка между ретраями
	private int deliveryTimeoutMs; // Таймаут доставки
	private int requestTimeoutMs; // Таймаут запроса
	private int maxInFlightRequests; // Макс. количество неподтвержденных запросов
	private Map<String, Object> config = new HashMap<>();
}
