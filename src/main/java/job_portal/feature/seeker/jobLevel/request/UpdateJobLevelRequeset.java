package job_portal.feature.seeker.jobLevel.request;
import lombok.Builder;

@Builder
public record UpdateJobLevelRequeset(
        String name,
        String alias
) {
}
