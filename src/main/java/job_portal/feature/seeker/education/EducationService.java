package job_portal.feature.seeker.education;

import job_portal.feature.seeker.education.dto.request.CreateEductionRequest;
import job_portal.feature.seeker.education.dto.request.UpdateEducationRequest;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;

public interface EducationService {
    
    void deleteById(Integer id);
    EducationRespone updateById(Integer id,UpdateEducationRequest updateEducationRequest);
    void createNewEducation(String uuid,CreateEductionRequest createEductionRequest);

}