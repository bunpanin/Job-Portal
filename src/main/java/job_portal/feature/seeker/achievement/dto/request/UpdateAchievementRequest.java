package job_portal.feature.seeker.achievement.dto.request;
import java.time.LocalDate;

import lombok.Builder;


@Builder
public record UpdateAchievementRequest(
    String achievementTittle,
    LocalDate date,
    String achievementDetail
) { 
}
