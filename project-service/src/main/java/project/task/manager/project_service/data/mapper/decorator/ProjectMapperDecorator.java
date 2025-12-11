package project.task.manager.project_service.data.mapper.decorator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.task.manager.project_service.data.entity.Project;
import project.task.manager.project_service.data.enums.ProjectStatus;
import project.task.manager.project_service.data.mapper.ProjectMapper;
import project.task.manager.project_service.data.request.ProjectRequestDto;

import java.time.ZonedDateTime;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Primary
@Setter
@Component
public abstract class ProjectMapperDecorator implements ProjectMapper {
	
	@Autowired
	@Qualifier("delegate")
	private ProjectMapper delegate;
	
	@Override
	public Project mapToEntity(ProjectRequestDto request) {
		Project project = delegate.mapToEntity(request);
		project.setProjectStatus(ProjectStatus.SUSPENDED);
		project.setCreated(ZonedDateTime.now());
		project.setUpdated(ZonedDateTime.now());
		
		return project;
	}
}
