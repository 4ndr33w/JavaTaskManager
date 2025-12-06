package project.task.manager.user_service.data.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import project.task.manager.user_service.configuration.openapi.constant.DtoSchemaMessages;
import project.task.manager.user_service.data.enums.Role;
import project.task.manager.user_service.util.constant.Validation;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record UserResponseDto(

				@Schema(description = DtoSchemaMessages.ID_DESCRIPTION, example = DtoSchemaMessages.ID_EXAMPLE)
        UUID id,
				@Schema(description = DtoSchemaMessages.NAME_DESCRIPTION, example = DtoSchemaMessages.NAME_EXAMPLE)
        String name,

				@Schema(description = DtoSchemaMessages.LAST_NAME_DESCRIPTION, example = DtoSchemaMessages.LAST_NAME_EXAMPLE)
        String lastName,

				@Schema(description = DtoSchemaMessages.EMAIL_DESCRIPTION, example = DtoSchemaMessages.EMAIL_EXAMPLE)
        String email,

				@Schema(description = DtoSchemaMessages.USERNAME_DESCRIPTION, example = DtoSchemaMessages.USERNAME_EXAMPLE)
        String userName,

				@JsonFormat(pattern = Validation.JSON_DATE_FORMAT)
				@Schema(description = DtoSchemaMessages.BIRTH_DATE_DESCRIPTION, example = DtoSchemaMessages.BIRTH_DATE_EXAMPLE)
        LocalDate birthDate,
        Set<Role> roles,

				@Schema(description = DtoSchemaMessages.PHONE_DESCRIPTION, example = DtoSchemaMessages.PHONE_EXAMPLE)
        String phone,

				@Schema(description = DtoSchemaMessages.HAS_IMAGE_DESCRIPTION, example = DtoSchemaMessages.HAS_IMAGE_EXAMPLE)
        boolean hasImage,

				@Schema(description = DtoSchemaMessages.IS_ACTIVE_DESCRIPTION, example = DtoSchemaMessages.IS_ACTIVE_EXAMPLE)
        boolean active,

				@Schema(description = DtoSchemaMessages.IS_BLOCKED_DESCRIPTION, example = DtoSchemaMessages.IS_BLOCKED_EXAMPLE)
        boolean blocked,
        byte[] image,
				@JsonFormat(pattern = Validation.JSON_DATE_TIME_FORMAT)
        OffsetDateTime created,
				@JsonFormat(pattern = Validation.JSON_DATE_TIME_FORMAT)
        OffsetDateTime updated) {
}