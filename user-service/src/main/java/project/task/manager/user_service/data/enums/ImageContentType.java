package project.task.manager.user_service.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageContentType {

    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    WEBP("image/webp");

    private final String value;
}