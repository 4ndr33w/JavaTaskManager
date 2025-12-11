package project.task.manager.user_service.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.enums.EventStatus;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
	
	List<Outbox> findAllByStatus(EventStatus status);
}
