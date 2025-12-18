package project.task.manager.user_service.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project.task.manager.user_service.data.enums.AuthorizationType;
import project.task.manager.user_service.data.enums.HttpHeaderKey;
import project.task.manager.user_service.exception.AuthenticationException;
import project.task.manager.user_service.exception.TokenValidationException;
import project.task.manager.user_service.exception.handler.SecurityExceptionHandler;
import project.task.manager.user_service.exception.util.Constant;
import project.task.manager.user_service.security.component.JwtTokenProvider;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

		private final String BEARER_PREFIX = "Bearer ";

		private final JwtTokenProvider  jwtTokenProvider;
		private final UserDetailsService userDetailsService;
		private final SecurityExceptionHandler exceptionHandler;

		@Override
		protected void doFilterInternal(
						@NonNull HttpServletRequest request,
						@NonNull HttpServletResponse response,
						@NonNull FilterChain filterChain) throws ServletException, IOException {
			log.info("Вызов JwtFilter.doFilterInternal с ресурса: {}", request.getRequestURL());
				try {
						String token = getTokenFromRequest(request);
						if (token == null) {
								filterChain.doFilter(request, response);
								return;
						}
						verifyToken(token);

						String username = jwtTokenProvider.getUserNameFromToken(token);
						UserDetails userDetails = userDetailsService.loadUserByUsername(username);

						UsernamePasswordAuthenticationToken authentication =
										new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						SecurityContextHolder.getContext().setAuthentication(authentication);

						filterChain.doFilter(request, response);

				} catch (Exception e) {
						log.error("Вызов JwtFilter.getTokenFromRequest: Ошибка при попытке аутенфицировать пользователя: {}", e.getMessage());
						exceptionHandler.unauthorizedHandler(request, response, e);
				}
		}

		private void verifyToken(String token) {
				boolean isTokenValid = jwtTokenProvider.validateAccessToken(token);

				if(!isTokenValid) {
						log.warn("Вызов JwtFilter.doFilterInternal: Токен не прошел валидацию");
						throw new TokenValidationException(Constant.INVALID_ACCESS_TOKEN_MESSAGE);
				}
		}

		private String getTokenFromRequest(HttpServletRequest request) {
				String bearer = request.getHeader(HttpHeaderKey.AUTHORIZATION.getValue());
				if (StringUtils.hasText(bearer) && bearer.startsWith(AuthorizationType.BEARER.getValue())) {
						return bearer.substring(BEARER_PREFIX.length());
				}
				return null;
		}
}