package job_portal.feature.seeker.achievement.dto.respone;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record AchievementRespone(
    Integer id,
    String achievementTittle,
    LocalDate date,
    String achievementDetail
) {
}