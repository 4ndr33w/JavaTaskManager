package project.task.manager.user_service.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.task.manager.user_service.controller.UserController;
import project.task.manager.user_service.data.request.ChangePasswordRequestDto;
import project.task.manager.user_service.data.request.UserRequestDto;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.ShortUserResponseDto;
import project.task.manager.user_service.data.response.UserPageViewResponseDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> getByEmail(String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByEmail(email));
    }

    @Override
    public ResponseEntity<UserResponseDto> getByUsername(String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUserName(username));
    }

    @Override
    public ResponseEntity<UserResponseDto> create(@Valid UserRequestDto request, MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request, image));
    }

		@Override
		public ResponseEntity<UserResponseDto> getById(UUID id) {

				return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
		}

    @Override
    public ResponseEntity<UserResponseDto> update(@Valid UserUpdateDto update, MultipartFile image) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(update, image));
    }

		@Override
		public ResponseEntity<List<UserResponseDto>> getAllByIds(List<UUID> userIds) {
				return ResponseEntity.status(HttpStatus.OK).body(userService.findAllByIds(userIds));
		}

    @Override
    public ResponseEntity<UserResponseDto> get() {
				return ResponseEntity.status(HttpStatus.OK).body(userService.find());
    }

    @Override
    public ResponseEntity<UserResponseDto> delete() {
				userService.delete();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<UserPageViewResponseDto> getPage(Integer page, Integer limit) {
				return ResponseEntity.status(HttpStatus.OK).body(userService.findAllByPage(page, limit));
    }

		@Override
		public ResponseEntity<Boolean> block() {
				return ResponseEntity.status(HttpStatus.OK).body(userService.block());
		}

		@Override
		public ResponseEntity<Boolean> deactivate() {
				return ResponseEntity.status(HttpStatus.OK).body(userService.deactivate());
		}

		@Override
		public ResponseEntity<Boolean> deleteImage() {
				return ResponseEntity.status(HttpStatus.OK).body(userService.deleteImage());
		}

		@Override
		public ResponseEntity<Boolean> changePassword(ChangePasswordRequestDto requestDto) {
				return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(requestDto));
		}

		@Override
		public ResponseEntity<Boolean> changeEmail(String newEmail) {
				return ResponseEntity.status(HttpStatus.OK).body(userService.changeEmail(newEmail));
		}

		@Override
		public ResponseEntity<Boolean> changeUsername(String newUsername) {
				return ResponseEntity.status(HttpStatus.OK).body(userService.changeUsername(newUsername));
		}

		@Override
		public ResponseEntity<String> dropPassword() {
				return ResponseEntity.status(HttpStatus.OK).body(userService.dropPassword());
		}
	
	@Override
	public ResponseEntity<ShortUserResponseDto> getShortUserInfo(UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getShortUserResponseDto(id));
	}
}