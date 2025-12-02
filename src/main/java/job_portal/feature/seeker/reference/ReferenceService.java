package job_portal.feature.seeker.reference;
import job_portal.feature.seeker.reference.dto.request.CreateReferenceRequest;
import job_portal.feature.seeker.reference.dto.request.UpdateReferenceRequest;

public interface ReferenceService {

    void deleteById(Integer id);
    void updateById(Integer id, UpdateReferenceRequest updateReferenceRequest);
    void createNewReference(String uuid,CreateReferenceRequest createReferenceRequest);

    
}