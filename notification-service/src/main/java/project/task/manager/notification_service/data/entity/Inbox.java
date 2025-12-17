package project.task.manager.notification_service.data.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import project.task.manager.notification_service.data.enums.EventStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "inbox")
@NoArgsConstructor
@AllArgsConstructor
public class Inbox {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(nullable = false)
	private UUID eventId;
	@Enumerated(EnumType.STRING)
	private EventStatus status;
	@Column(columnDefinition = "jsonb")
	@JdbcTypeCode(SqlTypes.JSON)
	private JsonNode payload;
	private ZonedDateTime receivedAt;
	private ZonedDateTime processedAt;
	
	@PrePersist
	protected void onCreate() {
		receivedAt = ZonedDateTime.now();
		status = EventStatus.RECEIVED;
	}
}