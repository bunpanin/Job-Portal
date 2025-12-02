package job_portal.feature.seeker.reference;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.domain.backend.seeker.SeekerReference;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.reference.dto.request.CreateReferenceRequest;
import job_portal.feature.seeker.reference.dto.request.UpdateReferenceRequest;
import job_portal.mapper.seeker.ReferenceMapper;
import job_portal.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService{

    private final SeekerRepository seekerRepository;
    private final ReferenceMapper referenceMapper;
    private final ReferenceRepository referenceRepository;
    private final JwtService jwtService;

    @Override          
    public void deleteById(Integer id) {
        Long seekerId = jwtService.extractUserId();
        SeekerReference seekerReference = referenceRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reference not found!")
        );
        if(!seekerReference.getSeeker().getId().equals(seekerId.intValue())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not delete this reference");
        }
        referenceRepository.delete(seekerReference);
    }

    @Override
    public void updateById(Integer id, UpdateReferenceRequest updateReferenceRequest) {
        Long seekerId = jwtService.extractUserId();
        SeekerReference seekerReference = referenceRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reference not found!")
        );

        if(!seekerReference.getSeeker().getId().equals(seekerId.intValue())){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "You do not update this reference"
            );
        }
        referenceMapper.fromReferenceUpdateRequest(updateReferenceRequest, seekerReference);
        referenceRepository.save(seekerReference);
    }

    @Override
    public void createNewReference(String uuid, CreateReferenceRequest createReferenceRequest) {
        
        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!")
        );
        SeekerReference reference = referenceMapper.fromCreateNewReference(createReferenceRequest);
        reference.setSeeker(seeker);
        reference.setCreatedAt(LocalDate.now());
        referenceRepository.save(reference);
    }


}
