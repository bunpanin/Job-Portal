package job_portal.feature.seeker.Auth.dto.request;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateRequest(
        String fullName,
        String email,
        String phoneNumber,
        String gender,
        LocalDate dob,
        Integer jobLevel,
        String address,
        String cityOrProvince,
        String country,
        String githubAccount,
        String linkAccount,
        String portfolio,
        String descriptionYourSelf,
        String cvFile
) {
}
