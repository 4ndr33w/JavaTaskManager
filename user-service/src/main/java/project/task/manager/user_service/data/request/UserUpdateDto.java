package project.task.manager.user_service.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import project.task.manager.user_service.configuration.openapi.constant.DtoSchemaMessages;
import project.task.manager.user_service.util.constant.RegexpPatterns;
import project.task.manager.user_service.util.constant.Validation;

import java.time.LocalDate;

public record UserUpdateDto(

        @Size(min = 1, max = 30)
        @Pattern(regexp = RegexpPatterns.REGEXP_NAME_PATTERN, message = RegexpPatterns.REGEXP_NAME_MESSAGE)
				@Schema(description = DtoSchemaMessages.NAME_DESCRIPTION, example = DtoSchemaMessages.NAME_EXAMPLE)
        String name,

				@Schema(description = DtoSchemaMessages.LAST_NAME_DESCRIPTION, example = DtoSchemaMessages.LAST_NAME_EXAMPLE)
        String lastName,

				@JsonFormat(pattern = Validation.JSON_DATE_FORMAT)
				@Past(message = Validation.VALIDATION_PAST_MESSAGE)
				@Schema(description = DtoSchemaMessages.BIRTH_DATE_DESCRIPTION, example = DtoSchemaMessages.BIRTH_DATE_EXAMPLE)
        LocalDate birthDate,

        @Size(min = 12, max = 20)
        @Pattern(regexp = RegexpPatterns.REGEXP_PHONE_PATTERN, message = RegexpPatterns.REGEXP_PHONE_MESSAGE)
				@Schema(description = DtoSchemaMessages.PHONE_DESCRIPTION, example = DtoSchemaMessages.PHONE_EXAMPLE)
        String phone
) {
}
