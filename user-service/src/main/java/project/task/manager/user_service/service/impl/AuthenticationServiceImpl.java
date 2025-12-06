package project.task.manager.user_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.repository.UserRepository;
import project.task.manager.user_service.data.response.AuthenticationResponseDto;
import project.task.manager.user_service.exception.AuthenticationException;
import project.task.manager.user_service.exception.TokenExpirationException;
import project.task.manager.user_service.exception.TokenValidationException;
import project.task.manager.user_service.exception.UserNotFoundException;
import project.task.manager.user_service.properties.JwtProperties;
import project.task.manager.user_service.security.AppUserDetails;
import project.task.manager.user_service.security.component.JwtTokenProvider;
import project.task.manager.user_service.service.AuthenticationService;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

		private final JwtTokenProvider tokenProvider;
		private final UserRepository userRepository;
		private final JwtProperties jwtProperties;

		@Override
		public AuthenticationResponseDto refreshAccessToken(@NonNull String refreshToken) {
				boolean isTokenValid = tokenProvider.validateRefreshToken(refreshToken);

				if(!isTokenValid) {
						throw new TokenValidationException("Токен не прошел валидацию");
				}

				UUID userId = tokenProvider.getUserIdFromRefreshToken(refreshToken);
				User existingUser = userRepository.findById(userId).orElseThrow(
								() -> new UserNotFoundException("Не найден пользователь с id: %s".formatted(userId.toString())));
				AppUserDetails userDetails = new AppUserDetails(existingUser);

				return new AuthenticationResponseDto(
								tokenProvider.refreshAccessToken(refreshToken, userDetails),
								tokenProvider.createRefreshToken(userDetails),
								jwtProperties.getTokenLifeTime(jwtProperties.getAccessTokenLifetime()),
								jwtProperties.getTokenLifeTime(jwtProperties.getRefreshTokenLifetime())
				);
		}
}