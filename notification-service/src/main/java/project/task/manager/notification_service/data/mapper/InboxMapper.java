package project.task.manager.notification_service.data.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.event.UserUpdatedEvent;
import project.task.manager.notification_service.data.mapper.decorator.InboxMapperDecorator;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Mapper(
		componentModel = "spring",
		uses = InboxMapperDecorator.class,
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		unmappedSourcePolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(InboxMapperDecorator.class)
public interface InboxMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "messageId", target = "eventId")
	Inbox mapUpdateEventToInbox(UserUpdatedEvent event);
}