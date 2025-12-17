package project.task.manager.notification_service.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.task.manager.notification_service.data.entity.Inbox;
import project.task.manager.notification_service.data.enums.EventStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Repository
public interface InboxRepository extends JpaRepository<Inbox, UUID> {
	
	List<Inbox> findAllByStatus(EventStatus status);
	Optional<Inbox> findByEventId(UUID eventId);
}
