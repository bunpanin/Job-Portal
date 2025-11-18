package job_portal.feature.seeker.auth.dto;
import lombok.Builder;

@Builder
public record DataRespone(
    String uuid,
    String fullName,
    String email,
    String phoneNumber,
    String password,
    String gender,
    String profile,
    String dob,
    String jobLevel,
    String address,
    String cityOrProvince,
    String country,
    String githubAccount,
    String linkInAccount,
    String portfolio,
    // String education,
    // String achievement,
    // String skill,
    // String role,
    // String language,
    // String reference,
    String descriptionYourSelf,
    String createdAt,
    String isVerified,
    String isBloked,
    String isAccountNonExpired,
    String isAccountNonLocked,
    String isCredentialsNonExpired
) {
    
}
