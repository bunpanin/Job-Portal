package job_portal.feature.seeker.workExperience;

import job_portal.feature.seeker.workExperience.dto.request.CreateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.request.UpdateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;

public interface WorkExperienceService {

    void deleteById(Integer id);
    WorkExperienceRespone updateById(Integer id, UpdateWorkExperienceRequest updateWorkExperienceRequest);
    void createNewExperience(String uuid ,CreateWorkExperienceRequest createWorkExperienceRequest);

}