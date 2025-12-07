package job_portal.feature.seeker.jobLevel.respone;

import java.time.LocalDate;

public record JobLevelRespone(
        Integer id,
        String name,
        String alias,
        LocalDate createdAt
) {
}
