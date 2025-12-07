package job_portal.feature.seeker.jobLevel;

import job_portal.domain.backend.seeker.JobLevel;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.jobLevel.request.CreateJobLevelRequest;
import job_portal.feature.seeker.jobLevel.request.UpdateJobLevelRequeset;
import job_portal.mapper.seeker.JobLevelMapper;
import job_portal.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class JobLevelServiceImpl implements  JobLevelService {

    private final SeekerRepository seekerRepository;
    private final JobLevelMapper jobLevelMapper;
    private final JobLevelRepository jobLevelRepository;

    @Override
    public void deleteJobLevel(Integer id) {

    }

    @Override
    public void updateJobLevel(Integer id, UpdateJobLevelRequeset updateJobLevelRequeset) {

    }

    @Override
    public void createNewJobLevel(String uuid, CreateJobLevelRequest createJobLevelRequest) {
        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found")
        );
        JobLevel jobLevel = jobLevelMapper.fromCreateNewJobLevel(createJobLevelRequest);
        jobLevel.setCreatedAt(LocalDate.now());
        jobLevelRepository.save(jobLevel);
    }
}
