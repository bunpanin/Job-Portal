package job_portal.feature.seeker.auth.dto.request;
import java.time.LocalDate;
import java.util.List;

import job_portal.feature.seeker.workExperience.dto.request.WorkExperienceRequest;
import lombok.Builder;

@Builder
public record SeekerUpdateRequest(
    String fullName,
    String phoneNumber,
    String gender,
    String profile,
    LocalDate dob,
    Integer jobLevel,
    String address,
    String cityOrProvince,
    String country,
    String githubAccount,
    String linkAccount,
    String portfolio,
    String education,
    String achievement,
    String skill,
    List<WorkExperienceRequest>  workExperienceRequests,
    String language,
    String reference,
    String descriptionYourSelf
) {
}