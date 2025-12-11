package project.task.manager.project_service.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.task.manager.project_service.data.enums.TaskPriority;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private ZonedDateTime created;
	private ZonedDateTime updated;
	private String taskName;
	private byte[] image;
	private ZonedDateTime startTime;
	private ZonedDateTime endTime;
	
	@Lob
	private byte[] file;
	@Enumerated(EnumType.STRING)
	private TaskPriority priority;
	private String fileName;
	private String column;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "desk_id", nullable = false)
	private Desk desk;
	private UUID creatorId;
	private UUID executorId;
}
