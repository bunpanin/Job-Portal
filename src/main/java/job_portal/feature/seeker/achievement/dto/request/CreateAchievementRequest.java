package job_portal.feature.seeker.achievement.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateAchievementRequest(
    @NotBlank(message = "AchievementTittle is required!")
    String achievementTittle,

    @NotNull(message = "Date is required!")
    LocalDate date,

    @NotBlank(message = "AchievementDetail is required!")
    String achievementDetail
){
    
}
