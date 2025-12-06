package project.task.manager.kafka_starter.autoConfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import project.task.manager.kafka_starter.config.KafkaProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 4ndr33w
 * @version 1.0
 */

@Slf4j
@EnableKafka
@AutoConfiguration
@RequiredArgsConstructor
@Import(KafkaConverterAutoConfiguration.class)
@EnableConfigurationProperties(KafkaProperties.class)
@ConditionalOnProperty(prefix = "properties.kafka", name = "bootstrap-servers")
@ConditionalOnClass({ KafkaTemplate.class, ConcurrentKafkaListenerContainerFactory.class })
public class KafkaAutoConfiguration {
	
	private final KafkaProperties properties;
	
	@Bean
	@ConditionalOnMissingBean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getProducer().getKeySerializer());
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getProducer().getValueSerializer());
		config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, properties.getProducer().isIdempotenceEnabled());
		config.put(ProducerConfig.ACKS_CONFIG, String.valueOf(properties.getProducer().getAcks()));
		config.put(ProducerConfig.RETRIES_CONFIG, properties.getProducer().getRetries());
		config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, properties.getProducer().getRetryBackoffMs());
		config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, properties.getProducer().getDeliveryTimeoutMs());
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, properties.getProducer().getRequestTimeoutMs());
		config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, properties.getProducer().getMaxInFlightRequests());
		config.putAll(properties.getProducer().getConfig());
		
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ConsumerFactory<String, Object> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		config.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getConsumer().getGroupId());
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getConsumer().getKeyDeserializer());
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getConsumer().getValueDeserializer());
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, properties.getConsumer().getMaxPollRecords());
		config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, properties.getConsumer().getMaxPollIntervalMs());
		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, properties.getConsumer().getSessionTimeoutMs());
		config.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, properties.getConsumer().getHeartbeatIntervalMs());
		config.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, properties.getConsumer().getFetchMaxBytes());
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, properties.getConsumer().isAutoCommitEnabled());
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, properties.getConsumer().getAutoCommitIntervalMs());
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getConsumer().getAutoOffsetReset());
		config.putAll(properties.getConsumer().getConfig());
		
		return new DefaultKafkaConsumerFactory<>(config);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
		KafkaTemplate<String, Object> template = new KafkaTemplate<>(producerFactory);
		template.setDefaultTopic(properties.getProducer().getTopic());
		return template;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
			ConsumerFactory<String, Object> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(1);
		factory.getContainerProperties().setPollTimeout(3000);
		factory.setCommonErrorHandler(errorHandler());
		return factory;
	}
	
	@Bean
	public DefaultErrorHandler errorHandler() {
		BackOff backOff = new FixedBackOff(
				properties.getConsumer().getRetryBackoffMs(),
				properties.getConsumer().getMaxRetries()
		);
		DefaultErrorHandler errorHandler = new DefaultErrorHandler(
				(record, exception) -> {
					log.error("Failed to process record: {}, exception: {}", record, exception.getMessage());
				},
				backOff
		);
		return errorHandler;
	}
}
