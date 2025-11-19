package job_portal.feature.seeker.auth.dto;
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