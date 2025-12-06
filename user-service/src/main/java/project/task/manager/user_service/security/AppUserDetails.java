package project.task.manager.user_service.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.task.manager.user_service.data.entity.User;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

		@Override
		public boolean isEnabled() {
				return user.isActive();
		}

		@Override
		public boolean isAccountNonLocked() {
				return !user.isBlocked();
		}
}