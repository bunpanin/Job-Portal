package job_portal.security;
import java.util.Collection;
import java.util.List;

import job_portal.domain.backend.company.Company;
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
    private Company company;
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return seeker.getRoles();
        if (seeker != null) {
            return seeker.getRoles();
        }
        if (company != null) {
            return company.getRoles();
        }
        // if (admin != null) {
        //     return admin.getRoles();
        // }
        return List.of();
    }

    @Override
    public String getPassword() {
        // return seeker.getPassword();

        if (seeker != null) return seeker.getPassword();
        if (company != null) return company.getPassword();
        // if (admin != null) return admin.getPassword();

        return null;
    }

    @Override
    public String getUsername() {
        // return seeker.getEmail();

        if (seeker != null) return seeker.getEmail();
        if (company != null) return company.getEmail();
        // if (admin != null) return admin.getEmail();

        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // return seeker.getIsAccountNonExpired();

        if (seeker != null) return seeker.getIsAccountNonExpired();
        if (company != null) return company.getIsAccountNonExpired();
        // if (admin != null) return admin.getIsAccountNonExpired();

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return seeker.getIsAccountNonLocked();

        if (seeker != null) return seeker.getIsAccountNonLocked();
        if (company != null) return company.getIsAccountNonLocked();
        // if (admin != null) return admin.getIsAccountNonLocked();

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // return seeker.getIsCredentialsNonExpired();
        if (seeker != null) return seeker.getIsCredentialsNonExpired();
        if (company != null) return company.getIsCredentialsNonExpired();
        // if (admin != null) return admin.getIsCredentialsNonExpired();

        return true;
    }

    @Override
    public boolean isEnabled() {
        // return !seeker.getIsDeleted();

        if (seeker != null) return !seeker.getIsDeleted();
        if (company != null) return !company.getIsDeleted();
        // if (admin != null) return !admin.getIsDeleted();

        return true;
    }


    
}