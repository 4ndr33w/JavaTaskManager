package project.task.manager.project_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import project.task.manager.project_service.data.response.UserDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@FeignClient(name = "${users.client.name}", url = "${users.client.host}")
public interface UserServiceClient {
	
	@GetMapping("/user-service/api/v1/admin/{userId}")
	ResponseEntity<UserDto> getUserById(@PathVariable UUID userId, @RequestHeader("Authorization") String authHeader);
	
	@PostMapping("/user-service/api/v1/users/ids")
	ResponseEntity<List<UserDto>> getUsersByIds(@RequestBody Set<UUID> userIds, @RequestHeader("Authorization") String authHeader);
}
