package project.task.manager.project_service.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.task.manager.project_service.data.enums.ProjectStatus;

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
@Table(name = "projects")
@NoArgsConstructor
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private ZonedDateTime created;
	private ZonedDateTime updated;
	private String projectName;
	private byte[] image;
	private String description;
	private UUID adminId;
	@Enumerated(EnumType.STRING)
	private ProjectStatus projectStatus;
	@ElementCollection
	@CollectionTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"))
	@Column(name = "user_id")
	private List<UUID> userIds;
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Desk> desks;
	
	@PrePersist
	protected void onCreate() {
		created = ZonedDateTime.now();
		updated = ZonedDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updated = ZonedDateTime.now();
	}
	
	public void addUser(UUID userId) {
		this.userIds.add(userId);
	}
	
	public void removeUser(UUID userId) {
		this.userIds.remove(userId);
	}
	
	public void addDesk(Desk desk) {
		desks.add(desk);
		desk.setProject(this);
	}
	
	public void removeDesk(Desk desk) {
		desks.remove(desk);
		desk.setProject(null);
	}
}
