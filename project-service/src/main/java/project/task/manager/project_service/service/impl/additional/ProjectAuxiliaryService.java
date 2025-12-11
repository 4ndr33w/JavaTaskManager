package project.task.manager.project_service.service.impl.additional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.task.manager.project_service.data.entity.Project;
import project.task.manager.project_service.data.repository.ProjectRepository;
import project.task.manager.project_service.data.response.UserDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProjectAuxiliaryService {
	
	private final ProjectRepository projectRepository;
	
	@Transactional(readOnly = true)
	public List<Project> getProjectEntitiesByAdminId(UUID adminId) {
		return projectRepository.findByAdminId(adminId);
	}
	
	public Set<UUID> getProjectsUsers(List<Project> projects) {
		return projects.stream()
				.map(Project::getUserIds)
				.flatMap(Collection ::stream)
				.collect(Collectors.toSet());
	}
	
	public Map<UUID, UserDto> groupUsersByProjectId(ResponseEntity<List<UserDto>> userResponse) {
		try {
			return userResponse.getBody().stream()
					.collect(Collectors.toMap(UserDto::id, Function.identity()));
		}
		catch (Exception e) {
			return Map.of();
		}
	}
	
	public List<UserDto> getProjectUsers(Project project, Map<UUID, UserDto> usersMap) {
		return project.getUserIds().stream()
				.map(usersMap::get)
				.filter(Objects ::nonNull)
				.collect(Collectors.toList());
	}
}
