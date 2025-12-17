package project.task.manager.notification_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.task.manager.notification_service.data.response.ShortUserResponseDto;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@FeignClient(name = "${users.client.name}", url = "${users.client.host}")
public interface UserServiceClient {
	
	@GetMapping("/user-service/api/v1/users/short/{userId}")
	ResponseEntity<ShortUserResponseDto> getUserById(@PathVariable UUID userId);
}
