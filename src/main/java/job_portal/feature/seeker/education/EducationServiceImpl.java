package job_portal.feature.seeker.education;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import job_portal.domain.backend.seeker.Degree;
import job_portal.domain.backend.seeker.Education;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.degree.DegreeRepository;
import job_portal.feature.seeker.education.dto.request.CreateEductionRequest;
import job_portal.feature.seeker.education.dto.request.UpdateEducationRequest;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;
import job_portal.mapper.seeker.EducationMapper;
import job_portal.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService{

    private final SeekerRepository seekerRepository;
    private final DegreeRepository degreeRepository;
    private final EducationMapper educationMapper;
    private final EducationRepository educationRepository;
    private final JwtService jwtService;
    // private final SeekerEducationRepository seekerEducationRepository;

    @Override
    public void deleteById(Integer id) {
        Long seekerId = jwtService.extractUserId();
        Education education = educationRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Education not found!")
        );
        if (!education.getSeeker().getId().equals(seekerId.intValue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "You do not update this education");
        }
        educationRepository.delete(education);
    }   

    @Override
    public EducationRespone updateById(Integer id, UpdateEducationRequest updateEducationRequest) {
        Long seekerId = jwtService.extractUserId();
        Education education = educationRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Education not found!")
        );
        if (!education.getSeeker().getId().equals(seekerId.intValue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "You do not update this education");
        }
        educationMapper.fromEducationUpdateRequest(updateEducationRequest, education);
        education = educationRepository.save(education);
        return educationMapper.toEducationRespone(education);
    }

    @Override
    public void createNewEducation(String uuid, CreateEductionRequest createEductionRequest) {
        // Validate
        Seeker seeker = seekerRepository.findByUuid(uuid)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!"));
        
        Degree degree = degreeRepository.findById(createEductionRequest.degree())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Degree not found!"));
        
        Education education = educationMapper.fromCreateNewEducation(createEductionRequest);
        education.setDegree(degree);
        education.setSeeker(seeker);

        educationRepository.save(education);
    }
}