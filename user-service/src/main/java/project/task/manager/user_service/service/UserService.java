package project.task.manager.user_service.service;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.request.ChangePasswordRequestDto;
import project.task.manager.user_service.data.request.UserRequestDto;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.ShortUserResponseDto;
import project.task.manager.user_service.data.response.UserPageViewResponseDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.exception.UserNotFoundException;
import project.task.manager.user_service.exception.SecurityContextHolderException;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для обслуживания операций с пользователями.
 * Содержит методы для создания, обновления, удаления и поиска пользователей, а также
 * просмотра списков пользователей постранично.
 */
public interface UserService {

		/**
		 * Создать профиль нового пользователя.
		 *
		 * @param request DTO с данными для создания профиля пользователя.
		 * @param image   Изображение пользователя (может быть пустым).
		 * @return Профиль созданного пользователя.
		 * @throws NullPointerException Если переданный параметр {@code UserRequestDto} не инициализирован.
		 */
    UserResponseDto create(UserRequestDto request, MultipartFile image);

		/**
		 * Обновляет данные собственного профиля пользователя.
		 * <p>
		 * Извлекает ID пользователя из контекста безопасности (SecurityContextHolder),
		 * находит соответствующего пользователя и обновляет его профиль.
		 *
		 * @param update DTO с обновленными данными пользователя.
		 * @return Обновленный профиль пользователя.
		 * @throws UserNotFoundException              Если пользователь не найден.
		 * @throws NullPointerException               Если переданное DTO не инициализировано.
		 * @throws SecurityContextHolderException    Если SecurityContextHolder не содержит данных или не инициализирован.
		 */
    UserResponseDto update(UserUpdateDto update, MultipartFile image);

		UserResponseDto findById(UUID id);

		/**
		 * Найти данные собственного профиля пользователя.
		 * <p>
		 * Извлекает ID пользователя из SecurityContextHolder и ищет соответствующий профиль.
		 *
		 * @return Найденный профиль пользователя.
		 * @throws UserNotFoundException              Если пользователь не найден.
		 * @throws SecurityContextHolderException    Если SecurityContextHolder не содержит данных или не инициализирован.
		 */
    UserResponseDto find();

		/**
		 * Удалить собственный профиль пользователя.
		 * <p>
		 * Извлекает ID пользователя из SecurityContextHolder и удаляет соответствующий профиль.
		 *
		 * @throws UserNotFoundException              Если пользователь не найден.
		 * @throws SecurityContextHolderException    Если SecurityContextHolder не содержит данных или не инициализирован.
		 */
    void delete();

		/**
		 * Получить представление страницы списка пользователей.
		 *
		 * @param page Номер страницы.
		 * @param limit Лимит отображаемых на одной странице пользователей.
		 * @return DTO, включающее:
		 * <ul>
		 *     <li>номер запрашиваемой страницы;</li>
		 *     <li>лимит пользователей на странице;</li>
		 *     <li>общее количество страниц (согласно указанному лимиту);</li>
		 *     <li>общее количество пользователей;</li>
		 *     <li>список пользователей на указанной странице.</li>
		 * </ul>
		 */
    UserPageViewResponseDto findAllByPage(Integer page, Integer limit);

		/**
		 * Получить профиль пользователя по его email.
		 *
		 * @param email Email пользователя, чей профиль требуется получить.
		 * @return DTO с данными профиля запрашиваемого пользователя.
		 * @throws UserNotFoundException Если пользователь не найден.
		 * @throws NullPointerException  Если переданный email не инициализирован.
		 */
    UserResponseDto findByEmail(String email);

		/**
		 * Получить профиль пользователя по его username.
		 *
		 * @param userName Username пользователя, чей профиль требуется получить.
		 * @return DTO с данными профиля запрашиваемого пользователя.
		 * @throws UserNotFoundException Если пользователь не найден.
		 * @throws NullPointerException  Если переданный username не инициализирован.
		 */
    UserResponseDto findByUserName(String userName);

		List<UserResponseDto> findAllByIds(List<UUID> userIds);

		/**
		 * Заблокировать собственный профиль пользователя.
		 *
		 * @return {@code true}, если блокировка выполнена успешно.
		 * @throws UserNotFoundException              Если пользователь не найден.
		 * @throws SecurityContextHolderException    Если SecurityContextHolder не содержит данных или не инициализирован.
		 */
     boolean block();

		/**
		 * Деактивировать собственный профиль пользователя.
		 *
		 * @return {@code true}, если деактивация выполнена успешно.
		 * @throws UserNotFoundException              Если пользователь не найден.
		 * @throws SecurityContextHolderException    Если SecurityContextHolder не содержит данных или не инициализирован.
		 */
    boolean deactivate();

		boolean deleteImage();

		boolean changePassword(ChangePasswordRequestDto requestDto);

		boolean changeEmail(String newEmail);

		boolean changeUsername(String newUsername);

		String dropPassword();
	
	/**
	 * Получить краткое представление пользователя по его ID.
	 * @param userId
	 * @return
	 */
	ShortUserResponseDto getShortUserResponseDto(UUID userId);
	
	/**
	 * Получить краткое представление списка пользователей по их ID.
	 * @param userId
	 * @return
	 */
	List<ShortUserResponseDto> getListOfShortUserResponseDtos(List<UUID> userId);


}