package job_portal.feature.seeker.auth.dto.respone;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;
import job_portal.feature.seeker.role.dto.respone.RoleRespone;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;
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
    List<WorkExperienceRespone> workExperienceRespones,
    List<EducationRespone> educationRespones,
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
