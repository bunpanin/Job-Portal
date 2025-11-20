package job_portal.feature.seeker.workExperience.dto.respone;
import java.time.LocalDate;

import lombok.Builder;
@Builder
public record WorkExperienceRespone(
    Integer id,
    String jobTitle,
    String jobLevel,
    String company,
    String typeOfExperience,
    String cityOrProvince,
    String country,
    LocalDate startDate,
    LocalDate endDate,
    String descriptionYourExperience
) {
}