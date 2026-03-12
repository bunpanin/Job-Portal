package job_portal.init;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import job_portal.domain.backend.Permission;
import job_portal.feature.permission.PermissionRepository;
import job_portal.feature.role.RoleRepository;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import job_portal.domain.backend.Role;
import job_portal.domain.backend.seeker.Degree;
import job_portal.domain.backend.seeker.JobLevel;
import job_portal.domain.backend.seeker.LanguageLevel;
import job_portal.domain.backend.seeker.TypeOfExperience;
import job_portal.feature.seeker.degree.DegreeRepository;
import job_portal.feature.seeker.jobLevel.JobLevelRepository;
import job_portal.feature.seeker.languageLevel.LanguageLevelRepository;
import job_portal.feature.seeker.typeOfExperience.TypeOfExperienceRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final JobLevelRepository jobLevelRepository;
    private final TypeOfExperienceRepository typeOfExperienceRepository;
    private final DegreeRepository degreeRepository;
    private final LanguageLevelRepository languageLevelRepository;
    private final PermissionRepository permissionRepository;

    @PostConstruct
    void init(){
        initRoleData();
        initJobLevelData();
        initTypeOfExperienceData();
        initDegreeData();
        initLanguageLevel();
        initPermission();
    }

    private  void initPermission(){
        Permission apply = Permission.builder()
                .name("seeker_apply_job")
                .description("Seeker Apply Job Permission")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createbyAlias("system")
                .createbyUuid("system")
                .build();
        Permission manageProfile = Permission.builder()
                .name("seeker_manage_profile")
                .description("Seeker Manage Profile Permission")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createbyAlias("system")
                .createbyUuid("system")
                .build();
        Permission uploadCV = Permission.builder()
                .name("seeker_cv_upload")
                .description("Seeker CV Upload Permission")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createbyAlias("system")
                .createbyUuid("system")
                .build();
        Permission trackApplication = Permission.builder()
                .name("seeker_track_application")
                .description("Seeker Track Application Permission")
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createbyAlias("system")
                .createbyUuid("system")
                .build();
//        Role seekerRole = roleRepository.findByName("SEEKER")
//                .orElseThrow(() -> new RuntimeException("Role SEEKER not found"));
//
//        seekerRole.setPermissions(new HashSet<>(savedPermissions));
//
//        roleRepository.save(seekerRole);
        permissionRepository.saveAll(List.of(apply, manageProfile,uploadCV,trackApplication));
        Role seekerRole = roleRepository.findByName("SEEKER").orElseThrow();
        seekerRole.setPermissions(Set.of(apply, manageProfile,uploadCV,trackApplication));
        roleRepository.save(seekerRole);
    }

    private void initLanguageLevel(){
        List<LanguageLevel> languageLevels = new ArrayList<>();
        languageLevels.add(
            LanguageLevel.builder()
            .name("Beginner")
            .alias("beginner")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        languageLevels.add(
            LanguageLevel.builder()
            .name("Intermediate")
            .alias("intermediate")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        languageLevels.add(
            LanguageLevel.builder()
            .name("Advanced")
            .alias("advanced")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        languageLevels.add(
            LanguageLevel.builder()
            .name("Native")
            .alias("native")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        languageLevelRepository.saveAll(languageLevels);
    }


    private void initDegreeData(){
        List<Degree> degrees = new ArrayList<>();
        degrees.add(
            Degree.builder()
            .name("High School")
            .alias("high-school")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        degrees.add(
            Degree.builder()
            .name("Associate")
            .alias("associate")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        degrees.add(
            Degree.builder()
            .name("Bachelor")
            .alias("bachelor")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        degrees.add(
            Degree.builder()
            .name("Master")
            .alias("master")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        degrees.add(
            Degree.builder()
            .name("Doctoral")
            .alias("doctoral")
            .createdAt(LocalDate.now())
            .isDeleted(false)
            .build()
        );
        degreeRepository.saveAll(degrees);
    }

    private void initRoleData(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder()
            .name("SEEKER")
            .uuid(UUID.randomUUID().toString())
            .alias("seeker")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build()
        );
        roles.add(Role.builder()
            .name("ADMIN_COMPANY")
            .uuid(UUID.randomUUID().toString())
            .alias("admin-company")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build()
        );
        roles.add(Role.builder()
            .name("MANAGER_COMPANY")
            .uuid(UUID.randomUUID().toString())
            .alias("manager-company")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build()
        );
        roles.add(Role.builder()
            .name("ADMIN")
            .uuid(UUID.randomUUID().toString())
            .alias("admin")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        roles.add(Role.builder()
            .name("SUPER_ADMIN")
            .uuid(UUID.randomUUID().toString())
            .alias("super-admin")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        roleRepository.saveAll(roles);
    }
    private void initJobLevelData(){
        List<JobLevel> jobLevels = new ArrayList<>();
        jobLevels.add(JobLevel.builder()
            .name("Internship")
            .alias("internship")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        jobLevels.add(JobLevel.builder()
            .name("Junior")
            .alias("junior")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        jobLevels.add(JobLevel.builder()
            .name("Mid")
            .alias("mid")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        jobLevels.add(JobLevel.builder()
            .name("Senior")
            .alias("senior")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        jobLevels.add(JobLevel.builder()
            .name("Manager")
            .alias("manager")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        jobLevels.add(JobLevel.builder()
            .name("Top Executive")
            .alias("top-executive")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        jobLevelRepository.saveAll(jobLevels);
    }

    private void initTypeOfExperienceData(){
        List<TypeOfExperience> typeOfExperiences = new ArrayList<>();
        typeOfExperiences.add(TypeOfExperience.builder()
            .uuid(UUID.randomUUID().toString())
            .alias("internship")
            .name("Internship")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        typeOfExperiences.add(TypeOfExperience.builder()
            .uuid(UUID.randomUUID().toString())
            .alias("volunteering")
            .name("Volunteering")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        typeOfExperiences.add(TypeOfExperience.builder()
            .uuid(UUID.randomUUID().toString())
            .alias("working-experience")
            .name("Working Experience")
            .isDeleted(false)
            .createdAt(LocalDate.now())
            .build());
        typeOfExperienceRepository.saveAll(typeOfExperiences);
    }
    
}