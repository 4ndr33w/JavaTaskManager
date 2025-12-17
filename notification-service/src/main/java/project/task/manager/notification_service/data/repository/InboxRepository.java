package project.task.manager.notification_service.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.task.manager.notification_service.data.entity.Inbox;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface InboxRepository extends JpaRepository<Inbox, UUID> {
}
