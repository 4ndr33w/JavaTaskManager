package project.task.manager.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// отключаем автоконфигурацию бинов в приложении для kafka-starter
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class})
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
