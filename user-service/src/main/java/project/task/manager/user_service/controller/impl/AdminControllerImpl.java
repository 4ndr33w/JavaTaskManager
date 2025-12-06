package project.task.manager.user_service.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.task.manager.user_service.controller.AdminController;
import project.task.manager.user_service.data.enums.Role;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.service.AdminService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

		private final AdminService adminService;

    @Override
    public ResponseEntity<UserResponseDto> updateById(UserUpdateDto request, UUID id) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminService.updateById(request, id));
    }

    @Override
    public ResponseEntity<UserResponseDto> getById(UUID id) {

				return ResponseEntity.status(HttpStatus.OK).body(adminService.findById(id));
    }

    @Override
    public ResponseEntity<UserResponseDto> deleteById(UUID id) {
        adminService.deleteById(id);
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

		@Override
		public ResponseEntity<Boolean> blockAccountById(UUID id) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.blockById(id));
		}

		@Override
		public ResponseEntity<Boolean> deactivateAccountById(UUID id) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.deactivateById(id));
		}

		@Override
		public ResponseEntity<Boolean> unblockAccountById(UUID id) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.unblockById(id));
		}

		@Override
		public ResponseEntity<Boolean> activateAccountById(UUID id) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.activateById(id));
		}

		@Override
		public ResponseEntity<Boolean> addRoleToUser(Role role, UUID userId) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.addRoleToUser(role, userId));
		}

		@Override
		public ResponseEntity<Boolean> removeRoleFromUser(Role role, UUID userId) {
				return ResponseEntity.status(HttpStatus.OK).body(adminService.removeRoleFromUser(role, userId));
		}
}