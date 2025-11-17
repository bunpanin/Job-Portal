package job_portal.init;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import job_portal.domain.backend.Role;
import job_portal.domain.backend.RoleRepository;
import job_portal.domain.backend.seeker.JobLevel;
import job_portal.feature.seeker.auth.JobLevelRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final JobLevelRepository jobLevelRepository;

    @PostConstruct
    void init(){
        initRoleData();
        initJobLevelData();
    }

    private void initRoleData(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().name("SEEKER").build());
        roles.add(Role.builder().name("ADMIN_COMPANY").build());
        roles.add(Role.builder().name("MANAGER_COMPANY").build());
        roles.add(Role.builder().name("ADMIN").build());
        roles.add(Role.builder().name("SUPER_ADMIN").build());
        roleRepository.saveAll(roles);
    }
    private void initJobLevelData(){
        List<JobLevel> jobLevels = new ArrayList<>();
        jobLevels.add(JobLevel.builder().name("Internship").alias("internship").build());
        jobLevels.add(JobLevel.builder().name("Junior").alias("junior").build());
        jobLevels.add(JobLevel.builder().name("Mid").alias("mid").build());
        jobLevels.add(JobLevel.builder().name("Senior").alias("senior").build());
        jobLevels.add(JobLevel.builder().name("Manager").alias("manager").build());
        jobLevels.add(JobLevel.builder().name("Top Executive").alias("top-executive").build());
        jobLevelRepository.saveAll(jobLevels);
    }
    
}
