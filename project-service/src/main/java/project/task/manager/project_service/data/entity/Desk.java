package project.task.manager.project_service.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "desks")
@NoArgsConstructor
public class Desk {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private ZonedDateTime created;
	private ZonedDateTime updated;
	private String deskName;
	private String description;
	private boolean privacy;
	private String[] columns;
	private UUID adminId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	@OneToMany(mappedBy = "desk", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("created ASC")
	private List<Task> tasks;
	
	@PrePersist
	protected void onCreate() {
		created = ZonedDateTime.now();
		updated = ZonedDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updated = ZonedDateTime.now();
	}
}
