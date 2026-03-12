package job_portal.feature.role;

import job_portal.domain.backend.Role;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.role.dto.request.CreateRoleRequest;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.util.SlugUtil;
import job_portal.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final SeekerRepository seekerRepository;
    private final RoleRepository roleRepository;

    @Override
    public void createNewRole(String uuid, CreateRoleRequest createRoleRequest) {

        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seeker not found!"
                )
        );

        Role role = Role.builder()
                .name(createRoleRequest.nane())
                .alias(SlugUtil.toSlug(createRoleRequest.nane()))
                .uuid(UuidUtil.generateUuid(createRoleRequest.nane()))
                .isDeleted(false)
                .createdAt(LocalDate.now())
                .build();
        roleRepository.save(role);
    }
}
