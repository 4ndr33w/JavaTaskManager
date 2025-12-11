package project.task.manager.project_service.data.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import project.task.manager.project_service.data.entity.Project;
import project.task.manager.project_service.data.mapper.decorator.ProjectMapperDecorator;
import project.task.manager.project_service.data.request.ProjectRequestDto;
import project.task.manager.project_service.data.response.ProjectResponseDto;
import project.task.manager.project_service.data.response.UserDto;

import java.util.List;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Mapper(
		componentModel = "spring",
		uses = ProjectMapperDecorator.class,
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		unmappedSourcePolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(ProjectMapperDecorator.class)
public interface ProjectMapper {
	Project mapToEntity(ProjectRequestDto request);
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "created", source = "entity.created")
	@Mapping(target = "updated", source = "entity.updated")
	@Mapping(target = "image", source = "entity.image")
	ProjectResponseDto mapToDto(Project entity, UserDto admin, List<UserDto> users);
	
	ProjectResponseDto mapToDto(Project entity);
}
