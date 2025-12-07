package job_portal.feature.seeker.jobLevel;

import job_portal.domain.backend.seeker.JobLevel;
import job_portal.feature.seeker.jobLevel.request.CreateJobLevelRequest;
import job_portal.feature.seeker.jobLevel.request.UpdateJobLevelRequeset;

public interface JobLevelService {

    void deleteJobLevel(Integer id);
    void updateJobLevel(Integer id, UpdateJobLevelRequeset updateJobLevelRequeset);
    void createNewJobLevel(String uuid, CreateJobLevelRequest createJobLevelRequest);
}
