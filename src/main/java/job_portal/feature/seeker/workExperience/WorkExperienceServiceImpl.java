package job_portal.feature.seeker.workExperience;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import job_portal.domain.backend.seeker.JobLevel;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.domain.backend.seeker.TypeOfExperience;
import job_portal.domain.backend.seeker.WorkExperience;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.jobLevel.JobLevelRepository;
import job_portal.feature.seeker.typeOfExperience.TypeOfExperienceRepository;
import job_portal.feature.seeker.workExperience.dto.request.CreateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.request.UpdateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;
import job_portal.mapper.seeker.WorkExperienceMapper;
import job_portal.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class WorkExperienceServiceImpl implements WorkExperienceService{

    private final SeekerRepository seekerRepository;
    private final JobLevelRepository jobLevelRepository;
    private final TypeOfExperienceRepository typeOfExperienceRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceMapper workExperienceMapper;
    private final JwtService jwtService;

    @Override
    public void deleteById(Integer id) {
        Long seekerId = jwtService.extractUserId();
        WorkExperience workExperience = workExperienceRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Experience not found!")
        );
        if (!workExperience.getSeeker().getId().equals(seekerId.intValue())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "You do not update this education");
        }
        workExperienceRepository.delete(workExperience);
    }

    @Override
    public WorkExperienceRespone updateById(Integer id, UpdateWorkExperienceRequest updateWorkExperienceRequest) {
        Long seekerId = jwtService.extractUserId();
         log.info("1: {}",seekerId);
        // Seeker seeker = seekerRepository.findById(seekerId.intValue()).orElseThrow(
        //     ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!")
        // );

        WorkExperience workExperience = workExperienceRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Experience not found!")
        );

        if (!workExperience.getSeeker().getId().equals(seekerId.intValue())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "You do not update this work experience");
        }

        workExperienceMapper.fromWorkExperienceUpdateRequest(updateWorkExperienceRequest, workExperience);
        if(updateWorkExperienceRequest.jobLevel() != null){
            JobLevel jobLevel = jobLevelRepository.findById(updateWorkExperienceRequest.jobLevel()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Level not found!")
            );
            workExperience.setJobLevel(jobLevel);
        }
        if(updateWorkExperienceRequest.typeOfExperience() != null){
            TypeOfExperience typeOfExperience = typeOfExperienceRepository.findById(updateWorkExperienceRequest.typeOfExperience()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Of Experience not found!")
            );
            workExperience.setTypeOfExperience(typeOfExperience);
        }

        workExperience = workExperienceRepository.save(workExperience);
        return workExperienceMapper.toWorkExperienceRespone(workExperience);
    }

    @Override
    public void createNewExperience(String uuid, CreateWorkExperienceRequest createWorkExperienceRequest) {

        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!")
        );
        JobLevel jobLevel = jobLevelRepository.findById(createWorkExperienceRequest.jobLevel()).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Level not found!")
        );
        TypeOfExperience typeOfExperience = typeOfExperienceRepository.findById(createWorkExperienceRequest.typeOfExperience()).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Of Experience not found!")
        );

        WorkExperience workExperience = workExperienceMapper.fromCreateNewWorkExperience(createWorkExperienceRequest);
        workExperience.setJobLevel(jobLevel);
        workExperience.setTypeOfExperience(typeOfExperience);
        workExperience.setSeeker(seeker);
        workExperienceRepository.save(workExperience);
    }    
}
