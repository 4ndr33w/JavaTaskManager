package project.task.manager.user_service.data.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.task.manager.user_service.data.enums.Role;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private LocalDate birthDate;
    private boolean hasImage;
		@Column(name = "is_active")
    private boolean active;
		@Column(name = "is_blocked")
    private boolean blocked;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private String phone;
		@CreatedDate
		@Column(updatable = false)
    private OffsetDateTime created;
		@LastModifiedDate
    private OffsetDateTime updated;

		@PrePersist
		protected void onCreate() {
				created = OffsetDateTime.now();
				updated = OffsetDateTime.now();
				active = true;
				roles.add(Role.GUEST);
		}

		@PreUpdate
		protected void onUpdate() {
				updated = OffsetDateTime.now();
		}
}