package project.task.manager.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import project.task.manager.user_service.configuration.openapi.constant.ApiResponseExample;
import project.task.manager.user_service.data.request.ChangePasswordRequestDto;
import project.task.manager.user_service.data.request.UserRequestDto;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.ShortUserResponseDto;
import project.task.manager.user_service.data.response.UserPageViewResponseDto;
import project.task.manager.user_service.data.response.UserResponseDto;
import project.task.manager.user_service.exception.dto.ErrorResponseDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/users")
public interface UserController {

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Пользователь успешно создан",
										content = @Content(
														schema = @Schema(implementation = UserResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = """
														1) Некорректный запрос;
														2) Пароль не прошел валидацию;
														3) Номер телефона не прошел валидацию;
														4) Имя не прошло валидацию;
														5) Имя пользователя (логин) не прошло валидацию;
														6) Дата рождения не прошла валидацию;
														7) Электронная почта не прошла валидацию
														""",
										content = @Content(
														examples = {
																		@ExampleObject(
																						name = "Некорректный запрос",
																						value = ApiResponseExample.BAD_REQUEST_EXAMPLE,
																						description = "Ошибка в запросе"),
																		@ExampleObject(
																						name = "Невалидный пароль",
																						value = ApiResponseExample.PASSWORD_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Пароль не прошел валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидный номер телефона",
																						value = ApiResponseExample.PHONE_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Номер телефона не прошел валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидное имя",
																						value = ApiResponseExample.NAME_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Имя не прошло валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидный логин",
																						value = ApiResponseExample.USERNAME_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Имя пользователя (логин) не прошло валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидная дата рождения",
																						value = ApiResponseExample.BIRTH_DATE_EXCEPTION_EXAMPLE,
																						description = "Дата рождения не прошла валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидная электронная почта",
																						value = ApiResponseExample.EMAIL_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Электронная почта не прошла валидацию"
																		)
														},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Operation(
            summary = "Создать профиль нового пользователя",
            description = "Создает профиль нового пользователя")
		@Tag(name = "CRUD-операции над собственным профилем аутенфицированного пользователя")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserResponseDto> create(
            @RequestPart("user")
            @Parameter(description = "JSON с пользовательскими данными",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRequestDto.class)))
            UserRequestDto request,
            @RequestPart(value = "image", required = false)
            @Parameter(description = "Изображение профиля пользователя (JPEG, PNG, GIF, JPG, WEBP) Size: 1MB MAX",
                    content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE))
            MultipartFile image);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Данные профиля пользователя по указанному id успешно получены",
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
						summary = "Получить данные собственного профиля пользователя",
						description = "Возвращает данные аутенфицированного пользователя")
		@Tag(name = "Операции менеджмента пользовательских профилей")
		@GetMapping("/{id}")
		ResponseEntity<UserResponseDto> getById(@PathVariable UUID id);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Пользовательские данные успешно обновлены",
										content = @Content(
														schema = @Schema(implementation = UserResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "400",
										description = """
														1) Некорректный запрос;
														2) Номер телефона не прошел валидацию;
														3) Имя не прошло валидацию;
														4) Дата рождения не прошла валидацию;
														""",
										content = @Content(
														examples = {
																		@ExampleObject(
																						name = "Некорректный запрос",
																						value = ApiResponseExample.BAD_REQUEST_EXAMPLE,
																						description = "Ошибка в запросе"),

																		@ExampleObject(
																						name = "Невалидный номер телефона",
																						value = ApiResponseExample.PHONE_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Номер телефона не прошел валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидное имя",
																						value = ApiResponseExample.NAME_VALIDATION_EXCEPTION_EXAMPLE,
																						description = "Имя не прошло валидацию"
																		),
																		@ExampleObject(
																						name = "Невалидная дата рождения",
																						value = ApiResponseExample.BIRTH_DATE_EXCEPTION_EXAMPLE,
																						description = "Дата рождения не прошла валидацию"
																		),
														},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @Operation(
            summary = "Обновить профиль аутенфицированного пользователя",
            description = "Обновляет профиль аутенфицированного пользователя")
		@Tag(name = "CRUD-операции над собственным профилем аутенфицированного пользователя")
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserResponseDto> update(
            @RequestPart("user")
            @Parameter(
										description = "JSON с пользовательскими данными",
										content = @Content(
														mediaType = MediaType.APPLICATION_JSON_VALUE,
														schema = @Schema(implementation = UserRequestDto.class)))
						UserUpdateDto update,
            @RequestPart(value = "image", required = false)
            @Parameter(
										description = "Изображение профиля пользователя (JPEG, PNG, GIF, JPG, WEBP) Size: 1MB MAX",
                    content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE))
            MultipartFile image);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Список пользователей успешно получен",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Получить список пользователей по набору Id",
						description = "Возвращает колекцию пользователей по переданному списку Id пользователей")
		@Tag(name = "Операции менеджмента пользовательских профилей")
		@PostMapping("/ids")
		@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
		ResponseEntity<List<UserResponseDto>> getAllByIds(@RequestBody List<UUID> userIds);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Данные собственного профиля пользователя успешно получены",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Получить данные собственного профиля пользователя",
						description = "Возвращает данные аутенфицированного пользователя")
		@Tag(name = "CRUD-операции над собственным профилем аутенфицированного пользователя")
    @GetMapping
    ResponseEntity<UserResponseDto> get();

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно получен профиль пользователя по его email",
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
						summary = "Получить данные профиля пользователя по его email",
						description = "Возвращает данные пользователя по переданному email")
		@Tag(name = "Операции менеджмента пользовательских профилей")
    @GetMapping("/email/{email}")
		@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    ResponseEntity<UserResponseDto> getByEmail(@RequestParam String email);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно получен профиль пользователя по его username",
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
						summary = "Получить данные профиля пользователя по его username",
						description = "Возвращает данные пользователя по переданному username")
		@Tag(name = "Операции менеджмента пользовательских профилей")
    @GetMapping("/username/{username}")
		@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    ResponseEntity<UserResponseDto> getByUsername(@RequestParam String username);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно удалён собственный профиль пользователя",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Удалить собственный профиль пользователя",
						description = "Удаляет профиль аутенфицированного пользователя")
		@Tag(name = "CRUD-операции над собственным профилем аутенфицированного пользователя")
    @DeleteMapping
    ResponseEntity<UserResponseDto> delete();

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно получена страница со списком пользователей",
										content = @Content(
														schema = @Schema(implementation = UserPageViewResponseDto.class),
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Получить постранично список пользователей",
						description = "Возвращает постраничное отображение списка пользователей с заданным лимитом")
		@Tag(name = "Операции менеджмента пользовательских профилей")
    @PostMapping("/page/{page}")
		@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    ResponseEntity<UserPageViewResponseDto> getPage(@RequestParam Integer page, @RequestParam Integer limit);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль успешно заблокирован",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Блокировка собственного профиля пользователя",
						description = "Блокирует профиль аутенфицированного пользователя")
		@Tag(name = "Операции управления состоянием собственного профиля аутенфицированного пользователя")
		@GetMapping("/block")
		ResponseEntity<Boolean> block();

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Профиль успешно деактивирован",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Деактивировать собственный профиль пользователя",
						description = "Деактивирует профиль аутенфицированного пользователя")
		@Tag(name = "Операции управления состоянием собственного профиля аутенфицированного пользователя")
		@GetMapping("/deactivate")
		ResponseEntity<Boolean> deactivate();

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Изображение успешно удалено",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Удалить изображение пользователя",
						description = "Удаляет сохранённое пользовательское изображение")
		@Tag(name = "Операции менеджмента собственного профиля аутенфицированного пользователя")
		@DeleteMapping("/image")
		ResponseEntity<Boolean> deleteImage();

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Пароль успешно изменён",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Изменить пароль",
						description = "Заменяет пароль пользователя на новый")
		@Tag(name = "Операции менеджмента собственного профиля аутенфицированного пользователя")
		@PostMapping("/change/password")
		ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequestDto requestDto);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Электронная почта успешно измена",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Изменить электронную почту пользователя",
						description = "Заменяет пользовательский email новым")
		@Tag(name = "Операции менеджмента собственного профиля аутенфицированного пользователя")
		@PostMapping("/change/email")
		ResponseEntity<Boolean> changeEmail(@RequestParam String newEmail);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Логин (userName) успешно изменён",
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
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Изменить логин (userName) пользователя",
						description = "Заменяет логин (userName) пользователя новым")
		@Tag(name = "Операции менеджмента собственного профиля аутенфицированного пользователя")
		@PostMapping("/change/username")
		ResponseEntity<Boolean> changeUsername(@RequestParam String newUsername);

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Пароль успешно сброшен",
										content = @Content(
														schema = @Schema(implementation = Boolean.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "401",
										description = "Требуется авторизация",
										content = @Content(
														examples = {@ExampleObject(value = ApiResponseExample.UNAUTHORIZED_EXAMPLE)},
														mediaType = MediaType.APPLICATION_JSON_VALUE)),
						@ApiResponse(
										responseCode = "500",
										description = "Внутренняя ошибка сервера",
										content = @Content(
														schema = @Schema(implementation = ErrorResponseDto.class),
														mediaType = MediaType.APPLICATION_JSON_VALUE))})
		@Operation(
						summary = "Сбросить пароль пользователя",
						description = "Сбрасывает пароль, генерируя случайное значение")
		@Tag(name = "Операции менеджмента собственного профиля аутенфицированного пользователя")
		@GetMapping("/drop")
		ResponseEntity<String> dropPassword();
	
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Данные успешно получены",
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
		@Operation(summary = "Получить краткую информацию о пользователе", description = "Возвращает краткую информацию о пользователе по его ID")
		@Tag(name = "Операции менеджмента пользовательских профилей")
		@GetMapping("/short/{id}")
		ResponseEntity<ShortUserResponseDto> getShortUserInfo(@PathVariable UUID id);
	
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Данные успешно получены",
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
					responseCode = "500",
					description = "Внутренняя ошибка сервера",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDto.class),
							mediaType = MediaType.APPLICATION_JSON_VALUE))})
	@Tag(name = "Операции менеджмента пользовательских профилей")
	@PostMapping("/short/list")
	ResponseEntity<List<ShortUserResponseDto>> getShortUsersListInfo(@RequestBody List<UUID> userIds);
}