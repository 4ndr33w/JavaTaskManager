package project.task.manager.project_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import project.task.manager.project_service.data.request.ProjectRequestDto;
import project.task.manager.project_service.data.response.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@RequestMapping("/api/v1/projects")
public interface ProjectController {
	
	@PostMapping
	ResponseEntity<ProjectResponseDto> create(@RequestBody ProjectRequestDto request);
	
	@GetMapping("/admin/{adminId}")
	ResponseEntity<List<ProjectResponseDto>> getByAdminId(
			@PathVariable UUID adminId,
			@RequestHeader("Authorization") String authHeader
	);
	
	@GetMapping("/user/{userId}")
	ResponseEntity<List<ProjectResponseDto>> getByParticipantId(
			@PathVariable UUID userId,
			@RequestHeader("Authorization") String authHeader
	);
}
