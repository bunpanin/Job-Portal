package job_portal.feature.seeker.education;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import job_portal.domain.backend.seeker.Degree;
import job_portal.domain.backend.seeker.Education;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.domain.backend.seeker.SeekerEducation;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.degree.DegreeRepository;
import job_portal.feature.seeker.education.dto.request.CreateEductionRequest;
import job_portal.feature.seeker.education.dto.request.UpdateEducationRequest;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;
import job_portal.mapper.seeker.EducationMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService{

    private final SeekerRepository seekerRepository;
    private final DegreeRepository degreeRepository;
    private final EducationMapper educationMapper;
    private final EducationRepository educationRepository;
    private final SeekerEducationRepository seekerEducationRepository;

    @Override
    public void deleteById(Integer id) {
        Education education = educationRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Education not found!")
        );
        educationRepository.delete(education);
    }   

    @Override
    public EducationRespone updateById(Integer id, UpdateEducationRequest updateEducationRequest) {
        Education education = educationRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Education not found!")
        );
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
        // Save to database Education
        educationRepository.save(education);

        SeekerEducation seekerEducation = new SeekerEducation();
        seekerEducation.setSeeker(seeker);
        seekerEducation.setEducation(education);
        seekerEducation.setCreatedAt(LocalDate.now());

        // Save to database SeekerEducation
        seekerEducationRepository.save(seekerEducation);
    } 
}