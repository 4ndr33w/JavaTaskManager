package project.task.manager.user_service.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.exception.AuthenticationException;
import project.task.manager.user_service.exception.TokenExpirationException;
import project.task.manager.user_service.exception.TokenValidationException;
import project.task.manager.user_service.exception.dto.ErrorResponseDto;
import project.task.manager.user_service.exception.util.Constant;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class SecurityExceptionHandler {
		private final ObjectMapper objectMapper;

		public void accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
				printResponse(response, new ErrorResponseDto(
								HttpStatus.FORBIDDEN.value(),
								Constant.FORBIDDEN_MESSAGE,
								ZonedDateTime.now()
				));
		}

		public void unauthorizedHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
				String message;

				if (ex instanceof TokenExpirationException) {
						message = Constant.ACCESS_TOKEN_EXPIRED_MESSAGE;
				} else if (ex instanceof TokenValidationException) {
						message = Constant.INVALID_ACCESS_TOKEN_MESSAGE;
				} else if (ex instanceof AuthenticationException) {
						message = ex.getMessage();
				} else {
						message = Constant.UNAUTHORIZED_MESSAGE;
				}
				printResponse(response, new ErrorResponseDto(
								HttpStatus.UNAUTHORIZED.value(),
								message,
								ZonedDateTime.now()
				));
		}

		private void printResponse (HttpServletResponse response, ErrorResponseDto responseDto) throws IOException {
				response.setStatus(responseDto.httpStatus());
				response.setContentType("application/json; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(responseDto));
		}
}