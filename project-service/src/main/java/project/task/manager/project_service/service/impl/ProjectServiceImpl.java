package project.task.manager.project_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.task.manager.project_service.client.UserServiceClient;
import project.task.manager.project_service.data.entity.Project;
import project.task.manager.project_service.data.mapper.ProjectMapper;
import project.task.manager.project_service.data.repository.ProjectRepository;
import project.task.manager.project_service.data.request.ProjectRequestDto;
import project.task.manager.project_service.data.response.ProjectResponseDto;
import project.task.manager.project_service.data.response.UserDto;
import project.task.manager.project_service.exception.ProjectNotFoundException;
import project.task.manager.project_service.service.ProjectService;
import project.task.manager.project_service.service.impl.additional.ProjectAuxiliaryService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
	
	private final UserServiceClient userClient;
	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;
	private final ProjectAuxiliaryService auxiliaryService;
	
	@Override
	public List<ProjectResponseDto> getByAdminId(UUID adminId, String authHeader) {
		ResponseEntity<UserDto> projectAdminResponse = userClient.getUserById(adminId, authHeader);
		
		if(projectAdminResponse.getStatusCode().is2xxSuccessful()) {
			UserDto projectAdmin = projectAdminResponse.getBody();
			List<Project> projects = auxiliaryService.getProjectEntitiesByAdminId(adminId);
			
			// составляем список Id участников всех проектов чтобы одним запросом из REST-клиента получить всех юзеров
			Set<UUID> participantIds = auxiliaryService.getProjectsUsers(projects);
			participantIds.add(adminId); // добавить админа к участникам
			
			ResponseEntity<List<UserDto>> participantsResponse = userClient.getUsersByIds(participantIds, authHeader);
			if(participantsResponse.getStatusCode().is2xxSuccessful() && participantsResponse.getBody() != null) {
				Map<UUID, UserDto> usersMap = auxiliaryService.groupUsersByProjectId(participantsResponse);
				
				return projects.stream()
						.map(project -> {
							// Получаем участников текущего проекта
							List<UserDto> projectUsers = auxiliaryService.getProjectUsers(project, usersMap);
							
							return projectMapper.mapToDto(project, projectAdmin, projectUsers);
						})
						.collect(Collectors.toList());
			}
		}
		return List.of();
	}
	
	@Override
	public List<ProjectResponseDto> getByParticipantId(UUID userId, String authHeader) {
		List<Project> projects = projectRepository.findByUserIdsContaining(userId);
		
		if (projects.isEmpty()) {
			return List.of();
		}
		Set<UUID> allUserIds = auxiliaryService.getProjectsUsers(projects);
		
		projects.stream()
				.map(Project::getAdminId)
				.forEach(allUserIds::add);
		
		ResponseEntity<List<UserDto>> usersResponse = userClient.getUsersByIds(allUserIds, authHeader);
		
		if (usersResponse.getStatusCode().is2xxSuccessful() && usersResponse.getBody() != null) {
			Map<UUID, UserDto> usersMap = auxiliaryService.groupUsersByProjectId(usersResponse);
			
			return projects.stream()
					.map(project -> {
						UserDto admin = usersMap.get(project.getAdminId());
						List<UserDto> projectUsers = auxiliaryService.getProjectUsers(project, usersMap);
						
						return projectMapper.mapToDto(project, admin, projectUsers);
					})
					.collect(Collectors.toList());
		}
		
		return List.of();
	}
	
	@Override
	public ProjectResponseDto getById(UUID id) {
		Project project = projectRepository.findById(id).orElseThrow(
				() -> new ProjectNotFoundException("Не найдено ни одного проекта с id: %s".formatted(id)));
		return projectMapper.mapToDto(project, null, null);
	}
	
	@Override
	public ProjectResponseDto updateById(UUID Id) {
		return null;
	}
	
	@Override
	public ProjectResponseDto deleteById(UUID Id) {
		return null;
	}
	
	@Override
	public ProjectResponseDto create(ProjectRequestDto requestDto) {
		Project project = projectRepository.save(projectMapper.mapToEntity(requestDto));
		return projectMapper.mapToDto(project, null, null);
	}
}
