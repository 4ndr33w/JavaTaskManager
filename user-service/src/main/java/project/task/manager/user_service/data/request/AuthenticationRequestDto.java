package project.task.manager.user_service.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record AuthenticationRequestDto(

				/**
				 * Электронная почта пользователя. Обязательное поле. Должна содержать действительный адрес электронной почты.
				 */
				@NotBlank
				@Email
				String email,

				@NotBlank
				//@JsonDeserialize(using = Base64PasswordDeserializer.class)
				String password
) {
}
