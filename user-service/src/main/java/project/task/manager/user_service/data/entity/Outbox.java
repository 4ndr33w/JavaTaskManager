package project.task.manager.user_service.data.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import project.task.manager.user_service.data.enums.EventStatus;
import project.task.manager.user_service.data.enums.EventType;
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
@Table(name = "outbox")
@NoArgsConstructor
@AllArgsConstructor
public class Outbox {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private UUID aggregateId;
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	@Enumerated(EnumType.STRING)
	private EventStatus status;
	@Column(columnDefinition = "jsonb")
	@JdbcTypeCode(SqlTypes.JSON)
	private JsonNode header;
	@Column(columnDefinition = "jsonb")
	@JdbcTypeCode(SqlTypes.JSON)
	private JsonNode payload;
	private Integer retryCount;
	private ZonedDateTime createdAt;
	private ZonedDateTime sentAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = ZonedDateTime.now();
		status = EventStatus.CREATED;
		retryCount = 0;
	}
}