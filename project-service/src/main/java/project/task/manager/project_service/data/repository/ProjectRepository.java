package project.task.manager.project_service.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.task.manager.project_service.data.entity.Project;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public interface ProjectRepository extends JpaRepository<Project, UUID> {
	
	List<Project> findByAdminId(UUID adminId);
	List<Project> findByUserIdsContaining(UUID userId);
}
