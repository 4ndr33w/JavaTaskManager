package project.task.manager.project_service.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.task.manager.project_service.configuration.openapi.constant.DtoSchemaMessages;
import project.task.manager.project_service.data.enums.UserRole;

import java.util.List;
import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
public record UserDto(@Schema(description = DtoSchemaMessages.ID_DESCRIPTION, example = DtoSchemaMessages.ID_EXAMPLE)
                      UUID id,
                      @Schema(description = DtoSchemaMessages.NAME_DESCRIPTION, example = DtoSchemaMessages.NAME_EXAMPLE)
                      String name,
                      
                      @Schema(description = DtoSchemaMessages.LAST_NAME_DESCRIPTION, example = DtoSchemaMessages.LAST_NAME_EXAMPLE)
                      String lastName,
                      
                      @Schema(description = DtoSchemaMessages.EMAIL_DESCRIPTION, example = DtoSchemaMessages.EMAIL_EXAMPLE)
                      String email,
                      
                      @Schema(description = DtoSchemaMessages.USERNAME_DESCRIPTION, example = DtoSchemaMessages.USERNAME_EXAMPLE)
                      String userName,
                      
                      @Schema(description = DtoSchemaMessages.BIRTH_DATE_DESCRIPTION, example = DtoSchemaMessages.BIRTH_DATE_EXAMPLE)
                      String birthDate,
                      List<UserRole> roles,
                      
                      @Schema(description = DtoSchemaMessages.PHONE_DESCRIPTION, example = DtoSchemaMessages.PHONE_EXAMPLE)
                      String phone,
                      
                      @Schema(description = DtoSchemaMessages.HAS_IMAGE_DESCRIPTION, example = DtoSchemaMessages.HAS_IMAGE_EXAMPLE)
                      boolean hasImage,
                      
                      @Schema(description = DtoSchemaMessages.IS_ACTIVE_DESCRIPTION, example = DtoSchemaMessages.IS_ACTIVE_EXAMPLE)
                      boolean active,
                      
                      @Schema(description = DtoSchemaMessages.IS_BLOCKED_DESCRIPTION, example = DtoSchemaMessages.IS_BLOCKED_EXAMPLE)
                      boolean blocked,
                      byte[] image,
                      
                      @Schema(description = DtoSchemaMessages.TIMESTAMP_DESCRIPTION, example = DtoSchemaMessages.TIMESTAMP_EXAMPLE)
                      String created,
                      
                      @Schema(description = DtoSchemaMessages.TIMESTAMP_DESCRIPTION, example = DtoSchemaMessages.TIMESTAMP_EXAMPLE)
                      String updated) {
}
