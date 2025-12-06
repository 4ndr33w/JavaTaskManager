package project.task.manager.user_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.task.manager.user_service.data.enums.Role;
import project.task.manager.user_service.exception.handler.SecurityExceptionHandler;
import project.task.manager.user_service.properties.JwtProperties;
import project.task.manager.user_service.security.component.JwtTokenProvider;
import project.task.manager.user_service.security.filter.JwtFilter;
import project.task.manager.user_service.security.filter.LoginAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

		private final SecurityExceptionHandler exceptionHandler;
		private final UserDetailsService userDetailsService;
		private final PasswordEncoder passwordEncoder;
		private final JwtTokenProvider tokenProvider;
		private final JwtProperties jwtProperties;
		private final ObjectMapper objectMapper;
		private final JwtFilter jwtFilter;

		@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
								.cors(AbstractHttpConfigurer::disable)
								.csrf(AbstractHttpConfigurer::disable)
								.authenticationProvider(authenticationProvider())
								.httpBasic(AbstractHttpConfigurer::disable)
								.formLogin(AbstractHttpConfigurer::disable)
								.sessionManagement(session ->
												session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
												.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
												.requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
												.requestMatchers("/api/v1/admin/**")
												.hasAuthority(Role.ADMIN.getAuthority())
                                .anyRequest().authenticated())
                .addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
								.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
								.exceptionHandling(ex ->
												ex.authenticationEntryPoint(exceptionHandler::unauthorizedHandler)
																.accessDeniedHandler(exceptionHandler::accessDeniedHandler))
								.build();
    }

		@Bean
		public AuthenticationProvider authenticationProvider() {
				DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
				daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
				return daoAuthenticationProvider;
		}

		@Bean
		public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
				LoginAuthenticationFilter filter = new LoginAuthenticationFilter(
								tokenProvider, userDetailsService, passwordEncoder, jwtProperties, objectMapper
				);
				filter.setAuthenticationManager(authenticationManager(null));
				filter.setFilterProcessesUrl("/api/v1/login");
				return filter;
		}

		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
				return configuration.getAuthenticationManager();
		}
}