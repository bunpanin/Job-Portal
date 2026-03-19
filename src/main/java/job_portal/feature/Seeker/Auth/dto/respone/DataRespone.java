package job_portal.feature.Seeker.Auth.dto.respone;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

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