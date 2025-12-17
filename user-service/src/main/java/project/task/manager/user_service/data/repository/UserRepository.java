package project.task.manager.user_service.data.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.projection.UserShortProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(@NonNull String email);

    Optional<User> findByUserName(@NonNull String userName);

		@Modifying
		@Query("UPDATE User u SET u.blocked = true, u.updated = CURRENT_TIMESTAMP WHERE u.id = :userId AND u.blocked = false")
		int blockUserById(UUID userId);

		@Modifying
		@Query("UPDATE User u SET u.active = false, u.updated = CURRENT_TIMESTAMP WHERE u.id = :userId AND u.active = true")
		int deactivateUserById(UUID userId);

		@Modifying
		@Query("UPDATE User u SET u.blocked = false, u.updated = CURRENT_TIMESTAMP WHERE u.id = :userId AND u.blocked = true")
		int unblockUserById(UUID userId);

		@Modifying
		@Query("UPDATE User u SET u.active = true, u.updated = CURRENT_TIMESTAMP WHERE u.id = :userId AND u.active = false")
		int activateUserById(UUID userId);

		@Modifying
		@Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
		int changePassword(String newPassword, UUID userId);

		@Modifying
		@Query("UPDATE User u SET u.email = :newEmail WHERE u.id = :userId")
		int changeEmail(String newEmail, UUID userId);

		@Modifying
		@Query("UPDATE User u SET u.userName = :newUserName WHERE u.id = :userId")
		int changeUsername(String newUserName, UUID userId);
		
		@Query("""
SELECT u.id as id, u.userName as userName, u.email as email, u.name as name, u.lastName as lastName FROM User u WHERE u.id = :id
""")
		Optional<UserShortProjection> findShortUserByUserId(@Param("id") UUID id);
		
		@Query("""
		SELECT u.id as id, u.userName as userName, u.email as email, u.name as name, u.lastName as lastName FROM User u WHERE u.id IN :ids
		""")
		List<UserShortProjection> findAllShortUsersByUserIds(@Param("ids") List<UUID> ids);
}