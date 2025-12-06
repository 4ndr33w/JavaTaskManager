package project.task.manager.kafka_starter.config.consumer;

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
@ConfigurationProperties(prefix = "properties.kafka.consumer")
public class Consumer {
	private String topic;
	private String groupId;
	private int maxPollRecords; // Макс. записей за один poll
	private int maxPollIntervalMs; // Макс. интервал между poll
	private int sessionTimeoutMs; // Таймаут сессии
	private int heartbeatIntervalMs; // Интервал heartbeat
	private int fetchMaxBytes; // Макс. размер выборки (50MB)
	private boolean autoCommitEnabled; // Автокоммит оффсетов
	private int autoCommitIntervalMs; // Интервал автокоммита
	private String autoOffsetReset; // Сброс оффсета
	private int retryBackoffMs; // Задержка между ретраями
	private int maxRetries; // Количество ретраев
	private Map<String, Object> config = new HashMap<>(); // Дополнительные настройки
	private String keyDeserializer;
	private String valueDeserializer;
}
