package project.task.manager.user_service.data.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    public String getValue() {
        return this.value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
