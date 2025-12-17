package project.task.manager.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import project.task.manager.notification_service.exception.KafkaDeserializationException;
import project.task.manager.notification_service.kafka.serializer.TaskManagerDeserializer;
import project.task.manager.notification_service.properties.NotificationKafkaProperties;

import java.util.Map;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaConfig {

	private final KafkaProperties kafkaProperties;
	private final NotificationKafkaProperties notificationKafkaProperties;

	/**
	 * Создает фабрику потребителей Kafka.
	 * @param deserializer Кастомный десериализатор для обработки входящих сообщений
	 * @return ConsumerFactory с настроенными параметрами
	 */
	@Bean
	public ConsumerFactory<String, Object> consumerFactory(TaskManagerDeserializer deserializer) {
		Map<String, Object> config = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer(),
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueDeserializer(),
				TaskManagerDeserializer.KEY_DESERIALIZER_CLASS, notificationKafkaProperties.getConsumer().errorHandleKeyDeserializer(),
				TaskManagerDeserializer.VALUE_DESERIALIZER_CLASS, notificationKafkaProperties.getConsumer().errorHandleValueDeserializer(),
				JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getConsumer().getProperties().get("spring.json.trusted.packages"),
				JsonDeserializer.USE_TYPE_INFO_HEADERS, kafkaProperties.getConsumer().getProperties().get("spring.json.use.type.headers"));
		
		return new DefaultKafkaConsumerFactory<>(
				config,
				new ErrorHandlingDeserializer<>(new StringDeserializer()),
				new ErrorHandlingDeserializer<>(deserializer)
		);
	}
	
	/**
	 * Создает фабрику для Kafka listener'ов.
	 * @param deserializer Кастомный десериализатор
	 * @param errorHandler Обработчик ошибок
	 * @return Настроенная фабрика listener'ов
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
			TaskManagerDeserializer deserializer,
			DefaultErrorHandler errorHandler
	) {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(consumerFactory(deserializer));
		factory.setCommonErrorHandler(errorHandler);
		
		return factory;
	}
	
	/**
	 * Создает обработчик для отправки неудачных сообщений в DLQ.
	 * @param kafkaTemplate Шаблон для отправки сообщений
	 * @return DeadLetterPublishingRecoverer
	 */
	@Bean
	public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<String, Object> kafkaTemplate) {
		return new DeadLetterPublishingRecoverer(kafkaTemplate);
	}
	
	/**
	 * Создает обработчик ошибок для Kafka listener'ов.
	 * @param deadLetterPublishingRecoverer Обработчик DLQ
	 * @return DefaultErrorHandler с настройками повторных попыток
	 */
	@Bean
	public DefaultErrorHandler errorHandler (DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
		DefaultErrorHandler errorHandler = new DefaultErrorHandler(deadLetterPublishingRecoverer,
				new FixedBackOff(1000L, 2));
		errorHandler.addNotRetryableExceptions(KafkaDeserializationException.class);
		errorHandler.setCommitRecovered(true);
		
		return errorHandler;
	}
}