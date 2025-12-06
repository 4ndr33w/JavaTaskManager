package project.task.manager.kafka_starter.autoConfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.task.manager.kafka_starter.config.KafkaMessageConverter;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class KafkaConverterAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public KafkaMessageConverter kafkaMessageConverter(ObjectMapper objectMapper) {
		return new KafkaMessageConverter(objectMapper);
	}
}
