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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.task.manager.user_service.configuration.openapi.constant.ApiResponseExample;
import project.task.manager.user_service.data.response.AuthenticationResponseDto;
import project.task.manager.user_service.exception.dto.ErrorResponseDto;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@RequestMapping("/api/v1/auth")
@Tag(
				name = "Контроллер обновления токена",
				description = "Предоставляет свежую пару токенов по валидному RefreshToken")
public interface AuthenticationController {

		@ApiResponses({
						@ApiResponse(
										responseCode = "200",
										description = "Успешно получена новая пара токенов",
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
						summary = "Получить свежую пару токенов",
						description = "Возвращает новую пару токенов")
		@PostMapping("/refresh")
		ResponseEntity<AuthenticationResponseDto> updateById(@RequestParam String refreshToken);
}