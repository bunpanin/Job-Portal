package job_portal.mapper.seeker;

import job_portal.domain.backend.seeker.JobLevel;
import job_portal.feature.seeker.jobLevel.request.CreateJobLevelRequest;
import job_portal.feature.seeker.jobLevel.request.UpdateJobLevelRequeset;
import job_portal.feature.seeker.jobLevel.respone.JobLevelRespone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JobLevelMapper {

    JobLevel fromCreateNewJobLevel(CreateJobLevelRequest createJobLevelRequest);
    void fromJobLevelUpdateRequest(UpdateJobLevelRequeset updateJobLevelRequest, @MappingTarget JobLevel jobLevel);
    JobLevelRespone toJobLevelRespone(JobLevel jobLevel);
}
