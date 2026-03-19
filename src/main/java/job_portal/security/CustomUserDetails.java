package job_portal.security;

import job_portal.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    // Your data
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRole userRole : user.getUserRoles()) {

            Role role = userRole.getRole();
            // Add role authority
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.getName())
            );
            // Add permission authorities
            for(RolePermission rolePermission : role.getRolePermissions()) {
                Permission permission = rolePermission.getPermission();
                authorities.add(
                        new SimpleGrantedAuthority(permission.getName())
                );
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsDeleted();
    }
}
