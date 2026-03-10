package job_portal.feature.permission;

import job_portal.domain.backend.Permission;
import job_portal.domain.backend.seeker.Achievement;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.permission.dto.request.CreatePermissionRequest;
import job_portal.feature.permission.dto.request.UpdatePermissionRequest;
import job_portal.feature.seeker.auth.SeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements  PermissionService {

    private final SeekerRepository seekerRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void deleteById(Integer id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Permission not found!"
        ));
        permission.setIsDeleted(true);
        permissionRepository.save(permission);
    }

    @Override
    public void updatePermissionById(Integer id, UpdatePermissionRequest updatePermissionRequest) {
        Permission permission = permissionRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Permission not found!"
        ));
        permission.setName(updatePermissionRequest.name());
        permission.setDescription(updatePermissionRequest.description());
        permissionRepository.save(permission);
    }

    @Override
    public void createNewPermission(String uuid, CreatePermissionRequest createPermissionRequest) {
        Seeker seeker = seekerRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!"));
        Permission permission = Permission.builder()
                .name(createPermissionRequest.nane())
                .description(createPermissionRequest.description())
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createbyAlias(createPermissionRequest.createdByAlias())
                .createbyUuid(uuid)
                .build();

        permissionRepository.save(permission);

//        Achievement achievement = achievementMapper.fromCreateAchievement(createAchievementRequest);
//        achievement.setCreatedAt(LocalDate.now());
//        achievement.setSeeker(seeker);
//        achievementRepository.save(achievement);
    }
}
