package project.task.manager.user_service.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.task.manager.user_service.configuration.openapi.constant.DtoSchemaMessages;

import java.util.List;

public record UserPageViewResponseDto(
				@Schema(description = DtoSchemaMessages.CURRENT_PAGE_DESCRIPTION, example = DtoSchemaMessages.CURRENT_PAGE_EXAMPLE)
        int currentPage,
				@Schema(description = DtoSchemaMessages.PAGE_LIMIT_DESCRIPTION, example = DtoSchemaMessages.PAGE_LIMIT_EXAMPLE)
        int limit,
				@Schema(description = DtoSchemaMessages.TOTAL_PAGES_DESCRIPTION, example = DtoSchemaMessages.TOTAL_PAGES_EXAMPLE)
        long totalPages,
				@Schema(description = DtoSchemaMessages.TOTAL_USERS_DESCRIPTION, example = DtoSchemaMessages.TOTAL_USERS_EXAMPLE)
        long totalUsers,
        List<UserResponseDto> users
) {
}