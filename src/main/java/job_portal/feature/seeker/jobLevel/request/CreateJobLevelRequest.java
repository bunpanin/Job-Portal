package job_portal.feature.seeker.jobLevel.request;
import lombok.Builder;

@Builder
public record CreateJobLevelRequest(
        String name,
        String alias
) {
}
