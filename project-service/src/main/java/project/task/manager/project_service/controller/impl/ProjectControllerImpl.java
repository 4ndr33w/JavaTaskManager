package project.task.manager.project_service.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.task.manager.project_service.controller.ProjectController;
import project.task.manager.project_service.data.request.ProjectRequestDto;
import project.task.manager.project_service.data.response.ProjectResponseDto;
import project.task.manager.project_service.service.ProjectService;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {
	
	private final ProjectService projectService;
	
	@Override
	public ResponseEntity<ProjectResponseDto> create(ProjectRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(request));
	}
	
	@Override
	public ResponseEntity<List<ProjectResponseDto>> getByAdminId(UUID adminId, String authHeader) {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.getByAdminId(adminId, authHeader));
	}
	
	@Override
	public ResponseEntity<List<ProjectResponseDto>> getByParticipantId(UUID userId, String authHeader) {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.getByParticipantId(userId, authHeader));
	}
}
