package project.task.manager.user_service.data.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.mapper.decorator.OutboxMapperDecorator;
import project.task.manager.user_service.data.request.UserUpdateDto;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Mapper(
		componentModel = "spring",
		uses = OutboxMapperDecorator.class,
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		unmappedSourcePolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(OutboxMapperDecorator.class)
public interface OutboxMapper {

	@Mapping(target = "id", ignore = true)
	Outbox mapUpdateToOutbox(User user, UserUpdateDto update);
}