package job_portal.feature.seeker.Auth.dto.respone;

import job_portal.feature.admin.Permission.dto.respone.PermissionRespone;
import job_portal.feature.admin.Role.dto.respone.RoleRespone;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DataRespone(
        String uuid,
        String fullName,
        String email,
        String phoneNumber,
        String password,
        String gender,
        LocalDate dob,

        String address,
        String cityOrProvince,
        String country,

        List<RoleRespone> roles,
        List<PermissionRespone> permissions,

        String githubAccount,
        String linkInAccount,
        String portfolio,
        String cvFile,

        LocalDateTime createdAt,
        Boolean isVerified,
        Boolean isBlocked,
        Boolean isAccountNonExpired,
        Boolean isAccountNonLocked,
        Boolean isCredentialsNonExpired
) {
}