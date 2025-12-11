package project.task.manager.project_service.service;

import project.task.manager.project_service.data.request.ProjectRequestDto;
import project.task.manager.project_service.data.response.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface ProjectService {
	
	List<ProjectResponseDto> getByAdminId(UUID adminId, String authHeader);
	List<ProjectResponseDto> getByParticipantId(UUID userId, String authHeader);
	ProjectResponseDto getById(UUID Id);
	ProjectResponseDto updateById(UUID Id);
	ProjectResponseDto deleteById(UUID Id);
	ProjectResponseDto create(ProjectRequestDto requestDto);
}
