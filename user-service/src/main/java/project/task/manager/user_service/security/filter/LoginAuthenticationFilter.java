package project.task.manager.user_service.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import project.task.manager.user_service.data.enums.AuthorizationType;
import project.task.manager.user_service.data.enums.HttpHeaderKey;
import project.task.manager.user_service.data.response.AuthenticationResponseDto;
import project.task.manager.user_service.exception.TokenValidationException;
import project.task.manager.user_service.properties.JwtProperties;
import project.task.manager.user_service.security.AppUserDetails;
import project.task.manager.user_service.security.component.JwtTokenProvider;
import project.task.manager.user_service.exception.AuthenticationException;

import java.util.Objects;

/**
 * @author 4ndr33w
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

		private final JwtTokenProvider tokenProvider;
		private final UserDetailsService  userDetailsService;
		private final PasswordEncoder passwordEncoder;
		private final JwtProperties properties;
		private final ObjectMapper  objectMapper;

		@PostConstruct
		public void init() {
				setFilterProcessesUrl("/api/v1/login");
		}

		@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
				String[] loginCredentials = getCredentialsFromHeaderValue(request);
				Objects.requireNonNull(loginCredentials); // если  getCredentialsFromHeaderValue вернул null сразу получим NPE без лишних мучений

				String login = loginCredentials[0];
				String password = loginCredentials[1];
				log.debug("Попытка аутенфикации по логину: '{}'", login);
				AppUserDetails userDetails =  getUserDetails(login);
				log.debug("Найден пользователь с id: '{}' по логину '{}'", userDetails.getUser().getId(), login);

				if(isPasswordMatches(password, userDetails)) {
						UsernamePasswordAuthenticationToken authRequest =
										new UsernamePasswordAuthenticationToken(login, password);
						authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						return this.getAuthenticationManager().authenticate(authRequest);
				}
				else {
						log.error("Пользователь с логином: '{}' неверно ввёл пароль", login);
						throw new TokenValidationException("Неверно введён пароль");
				}
		}

		@Override
		public void successfulAuthentication(HttpServletRequest request,
																				 HttpServletResponse response,
																				 FilterChain chain,
																				 Authentication authResult) {

				AppUserDetails userDetails = (AppUserDetails) authResult.getPrincipal();
				log.debug("Аутенфикация пользователя '{}' прошла успешно", userDetails.getUser().getId());

				AuthenticationResponseDto responseDto = new AuthenticationResponseDto(
								tokenProvider.createAccessToken(userDetails),
								tokenProvider.createRefreshToken(userDetails),
								getTokenLifeTime(properties.getAccessTokenLifetime()),
								getTokenLifeTime(properties.getRefreshTokenLifetime()));
				log.debug("Сгенерированы токены для пользователя '{}'", userDetails.getUser().getId());

				printResponse(response, responseDto);
		}

		private String[] getCredentialsFromHeaderValue(@NonNull HttpServletRequest request) {
				try {
						String header = request.getHeader(HttpHeaderKey.AUTHORIZATION.getValue());
						if(header.startsWith(AuthorizationType.BASIC.getValue())) {
								String credentials = new String(java.util.Base64.getDecoder().decode(header.substring(6)));
								return credentials.split(":", 2);
						}
				}
				catch (IllegalArgumentException e) {
						throw new AuthenticationException("Ошибка при извлечении логина и пароля из заголовка", e);
				}
				return null;
		}

		private AppUserDetails getUserDetails(String login) {
				return (AppUserDetails)userDetailsService.loadUserByUsername(login);

		}

		private boolean isPasswordMatches(String password, UserDetails userDetails) {
				return passwordEncoder.matches(password, userDetails.getPassword());
		}

		private Long getTokenLifeTime(Long millis) {
				return millis / 60_000 ;
		}

		private void printResponse(HttpServletResponse response, AuthenticationResponseDto responseDto) {
				try {
						String jsonResponse = objectMapper.writeValueAsString(responseDto);

						response.addHeader(
										HttpHeaderKey.AUTHORIZATION.getValue(),
										AuthorizationType.BEARER.getValue()  + " " + responseDto.accessToken());
						response.setContentType("application/json");
						response.getWriter().write(jsonResponse);
				}
				catch (Exception e) {
						log.error("Ошибка попытке вернуть токен. {}", e.getMessage());
						throw new AuthenticationException("Ошибка попытке вернуть токен", e);
				}
		}
}