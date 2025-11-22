package job_portal.feature.seeker.workExperience;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import job_portal.domain.backend.seeker.JobLevel;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.domain.backend.seeker.SeekerWorkExperience;
import job_portal.domain.backend.seeker.TypeOfExperience;
import job_portal.domain.backend.seeker.WorkExperience;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.jobLevel.JobLevelRepository;
import job_portal.feature.seeker.typeOfExperience.TypeOfExperienceRepository;
import job_portal.feature.seeker.workExperience.dto.request.CreateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.request.UpdateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;
import job_portal.mapper.seeker.WorkExperienceMapper;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService{

    private final SeekerRepository seekerRepository;
    private final JobLevelRepository jobLevelRepository;
    private final TypeOfExperienceRepository typeOfExperienceRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceMapper workExperienceMapper;
    private final SeekerWorkExperienceRepository seekerWorkExperienceRepository;

    @Override
    public void deleteById(Integer id) {
        WorkExperience workExperience = workExperienceRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Experience not found!")
        );
        workExperienceRepository.delete(workExperience);
    }

    @Override
    public WorkExperienceRespone updateById(Integer id, UpdateWorkExperienceRequest updateWorkExperienceRequest) {
        WorkExperience workExperience = workExperienceRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Experience not found!")
        );
        workExperienceMapper.fromWorkExperienceUpdateRequest(updateWorkExperienceRequest, workExperience);
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

        workExperienceRepository.save(workExperience);

        SeekerWorkExperience seekerWorkExperience = new SeekerWorkExperience();
        seekerWorkExperience.setSeeker(seeker);
        seekerWorkExperience.setWorkExperience(workExperience);
        seekerWorkExperience.setCreatedAt(LocalDate.now());

        seekerWorkExperienceRepository.save(seekerWorkExperience);
    }    
}
