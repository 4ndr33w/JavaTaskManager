package project.task.manager.user_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.task.manager.user_service.data.entity.Outbox;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.mapper.OutboxMapper;
import project.task.manager.user_service.data.mapper.UserMapper;
import project.task.manager.user_service.data.projection.UserShortProjection;
import project.task.manager.user_service.data.repository.OutboxRepository;
import project.task.manager.user_service.data.repository.UserRepository;
import project.task.manager.user_service.data.request.ChangePasswordRequestDto;
import project.task.manager.user_service.data.request.UserRequestDto;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.ShortUserResponseDto;
import project.task.manager.user_service.data.response.UserPageViewResponseDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.exception.PasswordDoesNotMatchException;
import project.task.manager.user_service.exception.SecurityContextHolderException;
import project.task.manager.user_service.exception.UserNotFoundException;
import project.task.manager.user_service.security.AppUserDetails;
import project.task.manager.user_service.service.ImageService;
import project.task.manager.user_service.service.UserService;
import project.task.manager.user_service.util.Utils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final OutboxRepository outboxRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ImageService imageService;
	private final OutboxMapper outboxMapper;
	private final UserMapper userMapper;
	private final Utils utils;
	
	/**
	 * Поиск пользователя по логину. В качестве логина может вы ступать:
	 * <ul>
	 *     <li>{@code email}</li>
	 *     <li>{@code userName}</li>
	 * </ul>
	 * @param login поле, идентифицирующее пользователя, чьи данные запрашиваются.
	 * @return объект, реализующий {@code UserDetails}, содержащий данные профиля пользователя
	 * @throws UserNotFoundException при неудачной попытке найти пользователя по {@code email} или {@code userName}
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(@NonNull String login) {
		try {
			User user = userRepository.findByUserName(login)
					.orElseGet(() ->userRepository.findByEmail(login)
							.orElseThrow(() ->
									new UsernameNotFoundException("Не найден пользователь с логином: %s".formatted(login)))
					);
			return new AppUserDetails(user);
		}
		catch (UsernameNotFoundException e) {
			throw new UserNotFoundException("Не найден пользователь с userName: %s".formatted(login), e);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserResponseDto findById(@NonNull UUID id) {
		User existingUser = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));
		
		return userMapper.mapToDto(existingUser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ShortUserResponseDto getShortUserResponseDto(@NonNull UUID userId) {
		UserShortProjection projection = userRepository.findShortUserByUserId(userId).orElseThrow(
				() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(userId.toString()))
		);
		return userMapper.mapProjectionToDto(projection);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ShortUserResponseDto> getListOfShortUserResponseDtos(@NonNull List<UUID> userIds) {
		List<UserShortProjection> projections = userRepository.findAllShortUsersByUserIds(userIds);
		return projections.stream().map(userMapper::mapProjectionToDto).toList();
	}
	
	@Override
	@Transactional
	public UserResponseDto create(@NonNull UserRequestDto request, MultipartFile image) {
		try {
			User user = userRepository.save(userMapper.mapRequestToEntity(request, isImageNonEmpty(image)));
			var savedImage = imageService.saveImage(user.getId(), image);
			
			if(savedImage != null) {
				return userMapper.mapToDto(user, savedImage);
			}
			return userMapper.mapToDto(user);
		}
		catch (Exception e) {
			log.error("Ошибка создания пользователя: {}", e.getMessage());
			throw new RuntimeException("Ошибка создания пользователя", e);
		}
	}
	
	@Override
	@Transactional
	public UserResponseDto update(@NonNull UserUpdateDto update, MultipartFile image) {
		UUID authenticatedUserId = getUserIdFromSecurityContext();
		User existingUser = userRepository.findById(authenticatedUserId)
				.orElseThrow(
						() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(authenticatedUserId))
				);
		if(image!= null) {
			if(!existingUser.isHasImage()) {
				imageService.saveImage(existingUser.getId(), image);
				existingUser.setHasImage(true);
			}
			else {
				imageService.updateImageByUserId(existingUser.getId(), image);
			}
		}
		User updatedUser = userMapper.mapUpdateToEntity(update, existingUser);
		Outbox outbox = outboxMapper.mapUpdateToOutbox(existingUser, update);
		var result = outboxRepository.save(outbox);
		if(existingUser.isHasImage()) {
			byte[] userImage = imageService.getImageByUserId(authenticatedUserId);
			return userMapper.mapToDto(updatedUser, userImage);
		}
		return userMapper.mapToDto(updatedUser);
	}
	
	// Ищем пользователя в БД, а не берём из SecurityContextHolder
	// так как после того как юзер залогинился
	// он мог изменить какие-либо данные
	// или админ мог изменить его данные
	// и нам нужно подгрузить из БД актуальный контент
	@Override
	@Transactional(readOnly = true)
	public UserResponseDto find() {
		UUID id = getUserIdFromSecurityContext();
		User existingUser = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(id.toString())));
		if(existingUser.isHasImage()) {
			byte[] userImage = imageService.getImageByUserId(id);
			return userMapper.mapToDto(existingUser, userImage);
		}
		return userMapper.mapToDto(existingUser);
	}
	
	@Override
	@Transactional
	public void delete() {
		User authenticatedUser = getUserFromSecurityContext();
		if(authenticatedUser.isHasImage()) {
			imageService.deleteImageByUserId(authenticatedUser.getId());
		}
		userRepository.deleteById(authenticatedUser.getId());
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserPageViewResponseDto findAllByPage(Integer page, Integer limit) {
		int pageLimit = setPageLimit(limit);
		int paginationPage = (page == null || page < 1) ? 1 : page - 1;
		Pageable pageable = PageRequest.of(paginationPage, pageLimit);
		Page<User> usersPage = userRepository.findAll(pageable);
		
		long totalUsers = userRepository.count();
		long totalPages = (totalUsers / pageLimit) + 1;
		
		List<User> userList = usersPage.stream().toList();
		List<UserResponseDto> usersWithImages = getUserResponseWithImages(userList);
		
		return new UserPageViewResponseDto(
				paginationPage + 1,
				pageLimit,
				totalPages,
				totalUsers,
				usersWithImages
		);
	}
	
	private List<UserResponseDto> getUserResponseWithImages(List<User> userList) {
		List<UUID> usersWithImagesIds = userList.stream()
				.filter(User::isHasImage)
				.map(User::getId)
				.toList();
		Map<UUID, byte[]> images = imageService.getImageListByIds(usersWithImagesIds);
		
		return userList.stream()
				.map(x -> {return userMapper.mapToDto(x, images.get(x.getId()));})
				.toList();
	}
	
	private int setPageLimit(Integer limit) {
		if(limit == null || limit < 1) {
			return 5;
		}
		if(limit > 20) {
			return 20;
		}
		return limit;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserResponseDto> findAllByIds(List<UUID> userIds) {
		List<User> users = userRepository.findAllById(userIds);
		return getUserResponseWithImages(users);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserResponseDto findByEmail(String email) {
		User existingUser = userRepository.findByEmail(email).orElseThrow(
				() -> new UserNotFoundException("Не найден пользователь с email: %s".formatted(email)));
		if(existingUser.isHasImage()) {
			byte[] userImage = imageService.getImageByUserId(existingUser.getId());
			return userMapper.mapToDto(existingUser, userImage);
		}
		return userMapper.mapToDto(getUserFromSecurityContext());
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserResponseDto findByUserName(String userName) {
		User existingUser = userRepository.findByUserName(userName).orElseThrow(
				() -> new UserNotFoundException("Не найден пользователь с email: %s".formatted(userName)));
		if(existingUser.isHasImage()) {
			byte[] userImage = imageService.getImageByUserId(existingUser.getId());
			return userMapper.mapToDto(existingUser, userImage);
		}
		return userMapper.mapToDto(getUserFromSecurityContext());
	}
	
	@Override
	@Transactional
	public boolean block() {
		User authenticatedUser = getUserFromSecurityContext();
		int updatedRows = userRepository.blockUserById(authenticatedUser.getId());
		return updatedRows > 0;
	}
	
	@Override
	@Transactional
	public boolean deactivate() {
		User authenticatedUser = getUserFromSecurityContext();
		int updatedRows = userRepository.deactivateUserById(authenticatedUser.getId());
		return updatedRows > 0;
	}
	
	private boolean isImageNonEmpty(MultipartFile image) {
		return image != null;
	}
	
	private User getUserFromSecurityContext() {
		try {
			return ((AppUserDetails)SecurityContextHolder
					.getContext()
					.getAuthentication()
					.getPrincipal())
					.getUser();
		}
		catch (Exception e) {
			throw new SecurityContextHolderException("Ошибка при извлечении данных пользователя", e);
		}
	}
	
	private UUID getUserIdFromSecurityContext() {
		try {
			return ((AppUserDetails)SecurityContextHolder
					.getContext()
					.getAuthentication()
					.getPrincipal())
					.getUser()
					.getId();
		}
		catch (Exception e) {
			throw new SecurityContextHolderException("Ошибка при извлечении данных пользователя", e);
		}
	}
	
	@Override
	public boolean deleteImage() {
		try {
			User authenticatedUser = getUserFromSecurityContext();
			if(authenticatedUser.isHasImage()) {
				imageService.deleteImageByUserId(authenticatedUser.getId());
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean changePassword(ChangePasswordRequestDto requestDto) {
		UUID currentUserId = getUserIdFromSecurityContext();
		User currentUser = userRepository.findById(currentUserId).orElseThrow(
				() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(currentUserId))
		);
		
		if(passwordEncoder.matches(requestDto.oldPassword(), currentUser.getPassword())) {
			String newPasswordHash = passwordEncoder.encode(requestDto.newPassword());
			return userRepository.changePassword(newPasswordHash, currentUserId) > 0;
		}
		throw new PasswordDoesNotMatchException("Старый пароль не совпадает с тем что хранится в базе");
	}
	
	@Override
	@Transactional
	public boolean changeEmail(String newEmail) {
		UUID currentUserId = getUserIdFromSecurityContext();
		return userRepository.changeEmail(newEmail, currentUserId) > 0;
	}
	
	@Override
	@Transactional
	public boolean changeUsername(String newUsername) {
		UUID currentUserId = getUserIdFromSecurityContext();
		return userRepository.changeUsername(newUsername, currentUserId) > 0;
	}
	
	@Override
	@Transactional
	public String dropPassword() {
		UUID currentUserId = getUserIdFromSecurityContext();
		
		String newRawPassword = utils.mapRandomPassword();
		String hashedNewPassword = passwordEncoder.encode(newRawPassword);
		
		if(userRepository.changePassword(hashedNewPassword, currentUserId) > 0) {
			return newRawPassword;
		}
		return "";
	}
}