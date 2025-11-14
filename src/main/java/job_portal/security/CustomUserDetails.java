package job_portal.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import job_portal.domain.backend.seeker.Seeker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Seeker seeker;
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return seeker.getRoles();
    }

    @Override
    public String getPassword() {
        return seeker.getPassword();
    }

    @Override
    public String getUsername() {
        return seeker.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return seeker.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return seeker.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return seeker.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return !seeker.getIsDeleted();
    }


    
}