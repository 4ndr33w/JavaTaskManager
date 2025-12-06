package project.task.manager.user_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.enums.Role;
import project.task.manager.user_service.data.mapper.UserMapper;
import project.task.manager.user_service.data.repository.UserRepository;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.exception.UserNotFoundException;
import project.task.manager.user_service.exception.UserRoleException;
import project.task.manager.user_service.service.AdminService;
import project.task.manager.user_service.service.ImageService;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
		private final ImageService imageService;
    private final UserMapper userMapper;

    @Override
		@Transactional(readOnly = true)
    public UserResponseDto findById(@NonNull UUID id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));

        return userMapper.mapToDto(existingUser);
    }

    @Override
		@Transactional
    public boolean deleteById(@NonNull UUID id) {
				try {
						User existingUser = userRepository.findById(id).orElseThrow(
										() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));
						if(existingUser.isHasImage()) {
								imageService.deleteImageByUserId(id);
						}
						userRepository.deleteById(id);
						return true;
				}
				catch (Exception e) {
						throw new RuntimeException("Ошибка при удалении пользователя с id: %s".formatted(id), e);
				}
    }

    @Override
		@Transactional
    public UserResponseDto updateById(@NonNull UserUpdateDto updateDto, @NonNull UUID id) {
				User existingUser = userRepository.findById(id).orElseThrow(
								() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));
				User updatedUser = userMapper.mapUpdateToEntity(updateDto, existingUser);
				if(updatedUser.isHasImage()) {
						byte[] image = imageService.getImageByUserId(id);
						return userMapper.mapToDto(updatedUser, image);
				}
        return userMapper.mapToDto(existingUser);
    }

    @Override
		@Transactional
    public boolean blockById(@NonNull UUID id) {
				int updatedRows = userRepository.blockUserById(id);
        return updatedRows > 0;
    }

    @Override
		@Transactional
    public boolean deactivateById(@NonNull UUID id) {
				int updatedRows = userRepository.deactivateUserById(id);
        return updatedRows > 0;
    }

		@Override
		@Transactional
		public boolean unblockById(@NonNull UUID id) {
				int updatedRows = userRepository.unblockUserById(id);
				return updatedRows > 0;
		}

		@Override
		@Transactional
		public boolean activateById(@NonNull UUID id) {
				int updatedRows = userRepository.activateUserById(id);
				return updatedRows > 0;
		}

		@Override
		@Transactional
		public boolean addRoleToUser(@NonNull Role role, @NonNull UUID id) {
				User existingUser = userRepository.findById(id).orElseThrow(
								() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));
				if(!existingUser.getRoles().contains(role)) {
						existingUser.getRoles().add(role);
						return true;
				}
				throw new UserRoleException("У пользователя с id: %s уже имеется роль '%s'".formatted(id, role));
		}

		@Override
		@Transactional
		public boolean removeRoleFromUser(@NonNull Role role, @NonNull UUID id) {
				User existingUser = userRepository.findById(id).orElseThrow(
								() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));
				if(existingUser.getRoles().contains(role)) {
						existingUser.getRoles().remove(role);
						return true;
				}
				throw new UserRoleException("У пользователя с id: %s отсутствует роль '%s'".formatted(id, role));
		}
}