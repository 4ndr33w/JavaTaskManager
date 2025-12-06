package project.task.manager.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.task.manager.user_service.configuration.openapi.constant.ApiResponseExample;
import project.task.manager.user_service.data.enums.Role;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.exception.dto.ErrorResponseDto;

import java.util.UUID;

@RequestMapping("/api/v1/admin")
@Tag(
				name = "Контроллер администрирования пользователей",
				description = "REST API контроллер администрирования пользовательских данных")
public interface AdminController {

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Пользовательские данные успешно обновлены",
										content = @Content(
														schema = @Schema(implementation = UserResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Обновить профиль пользователя по его Id",
						description = "Обновляет профиль пользователя по его Id")
    @PatchMapping("/{id}")
    ResponseEntity<UserResponseDto> updateById(@RequestBody UserUpdateDto request, @PathVariable UUID id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно получены данные профиля пользователя",
										content = @Content(
														schema = @Schema(implementation = UserResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Получить профиль пользователя по его Id",
						description = "Возвращает профиль пользователя по его Id")
    @GetMapping("/{id}")
    ResponseEntity<UserResponseDto> getById(@PathVariable UUID Id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль пользователя успешно удалён",
										content = @Content(
														schema = @Schema(implementation = UserResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Удалить профиль пользователя по его Id",
						description = "Удаляет профиль пользователя по его Id")
    @DeleteMapping("/{id}")
    ResponseEntity<UserResponseDto> deleteById(@PathVariable UUID Id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль пользователя успешно заблокирован",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Заблокировать профиль пользователя по его Id",
						description = "Блокирует профиль пользователя по его Id")
		@GetMapping("/block/{id}")
		ResponseEntity<Boolean> blockAccountById(@PathVariable UUID id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль пользователя успешно деактивирован",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Деактивировать профиль пользователя по его Id",
						description = "Деактивирует профиль пользователя по его Id")
		@GetMapping("/deactivate/{id}")
		ResponseEntity<Boolean> deactivateAccountById(@PathVariable UUID id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль пользователя успешно разблокирован",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Разблокировать профиль пользователя по его Id",
						description = "Разблокирует профиль пользователя по его Id")
		@GetMapping("/unblock/{id}")
		ResponseEntity<Boolean> unblockAccountById(@PathVariable UUID id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль пользователя успешно активирован",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Активировать профиль пользователя по его Id",
						description = "Активирует профиль пользователя по его Id")
		@GetMapping("/activate/{id}")
		ResponseEntity<Boolean> activateAccountById(@PathVariable UUID id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно добавлена роль пользователю",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Добавить пользователю роль",
						description = "Добавлет роль пользователю")
		@PostMapping("/role/add")
		ResponseEntity<Boolean> addRoleToUser(@RequestParam Role role, @RequestParam UUID userId);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно удалена роль пользователя",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = "Некорректный запрос",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.BAD_REQUEST_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "403",
										description = "Нет прав на выполнение операции",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.FORBIDDEN_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "404",
										description = "Пользователь не найден",
										content = @Content(
														examples = {
																		@ExampleObject(value = ApiResponseExample.USER_NOT_FOUND_BY_ID_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Убрать роль у пользователя",
						description = "Убирает роль у пользователю")
		@PostMapping("/role/remove")
		ResponseEntity<Boolean> removeRoleFromUser(@RequestParam Role role, @RequestParam UUID userId);
}