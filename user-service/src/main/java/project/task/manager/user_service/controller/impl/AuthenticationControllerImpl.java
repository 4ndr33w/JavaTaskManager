package project.task.manager.user_service.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.task.manager.user_service.controller.AuthenticationController;
import project.task.manager.user_service.data.response.AuthenticationResponseDto;
import project.task.manager.user_service.service.AuthenticationService;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

		private final AuthenticationService authenticationService;

		@Override
		public ResponseEntity<AuthenticationResponseDto> updateById(String refreshToken) {
				return ResponseEntity.status(HttpStatus.OK).body(authenticationService.refreshAccessToken(refreshToken));
		}
}