package project.task.manager.user_service.service;

import project.task.manager.user_service.data.enums.Role;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.exception.UserNotFoundException;

import java.util.UUID;

/**
 * Сервис административных операций над профилями пользователей.
 * Предоставляет методы для поиска, удаления, обновления и блокировки профилей пользователей.
 */
public interface AdminService {

		/**
		 * Находит профиль пользователя по его уникальному идентификатору.
		 *
		 * @param id уникальный идентификатор пользователя, профиль которого требуется найти.
		 * @return DTO с данными найденного профиля пользователя.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если переданный параметр {@code id} равен {@code null}.
		 */
    UserResponseDto findById(UUID id);

		/**
		 * Удаляет профиль пользователя по его уникальному идентификатору.
		 *
		 * @param id уникальный идентификатор пользователя, профиль которого требуется удалить.
		 * @return {@code true}, если удаление произошло успешно.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если переданный параметр {@code id} равен {@code null}.
		 */
    boolean deleteById(UUID id);

		/**
		 * Обновляет профиль пользователя по его уникальному идентификатору.
		 *
		 * @param updateDto DTO с обновлёнными данными пользователя.
		 * @param id        уникальный идентификатор пользователя, профиль которого требуется обновить.
		 * @return DTO с обновлёнными данными профиля пользователя.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если хотя бы один из параметров ({@code updateDto} или {@code id}) равен {@code null}.
		 */
    UserResponseDto updateById(UserUpdateDto updateDto, UUID id);

		/**
		 * Блокирует профиль пользователя по его уникальному идентификатору.
		 *
		 * @param id уникальный идентификатор пользователя, профиль которого требуется заблокировать.
		 * @return {@code true}, если блокировка прошла успешно.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если переданный параметр {@code id} равен {@code null}.
		 */
    boolean blockById(UUID id);

		/**
		 * Разблокирует профиль пользователя по его уникальному идентификатору.
		 *
		 * @param id уникальный идентификатор пользователя, профиль которого требуется разблокировать.
		 * @return {@code true}, если разблокировка прошла успешно.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если переданный параметр {@code id} равен {@code null}.
		 */
		boolean unblockById(UUID id);

		/**
		 * Активирует профиль пользователя по его уникальному идентификатору.
		 *
		 * @param id уникальный идентификатор пользователя, профиль которого требуется активировать.
		 * @return {@code true}, если активация прошла успешно.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если переданный параметр {@code id} равен {@code null}.
		 */
    boolean activateById(UUID id);

		/**
		 * Деактивирует профиль пользователя по его уникальному идентификатору.
		 *
		 * @param id уникальный идентификатор пользователя, профиль которого требуется деактивировать.
		 * @return {@code true}, если деактивация прошла успешно.
		 * @throws UserNotFoundException если пользователь с таким идентификатором не найден.
		 * @throws NullPointerException  если переданный параметр {@code id} равен {@code null}.
		 */
		boolean deactivateById(UUID id);

		boolean addRoleToUser(Role role, UUID id);

		boolean removeRoleFromUser(Role role, UUID id);
}