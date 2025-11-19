package job_portal.feature.seeker.auth.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.management.relation.Role;

import job_portal.domain.backend.seeker.SeekerWorkExperience;
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
    LocalDate dob,
    String jobLevel,
    String address,
    String cityOrProvince,
    String country,
    String githubAccount,
    String linkInAccount,
    String portfolio,
    List<RoleRespone> roles,
    List<WorkExperienceRespone> workExperienceRequests,
    // String education,
    // String achievement,
    // String skill,
    // String role,
    // String language,
    // String reference,
    String descriptionYourSelf,
    // List<> role,
    LocalDateTime createdAt,
    Boolean isVerified,
    Boolean isBloked,
    Boolean isAccountNonExpired,
    Boolean isAccountNonLocked,
    Boolean isCredentialsNonExpired
) {
    
}
