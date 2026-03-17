package job_portal.feature.rolePermission;

import job_portal.domain.backend.Permission;
import job_portal.domain.backend.Role;
import job_portal.domain.backend.RolePermission;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.permission.PermissionRepository;
import job_portal.feature.role.RoleRepository;
import job_portal.feature.rolePermission.dto.request.CreateRolePermissionRequest;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.security.JwtService;
import job_portal.util.SlugUtil;
import job_portal.util.TextUtil;
import job_portal.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolePermissionServiceImpl implements  RolePermissionService{

    private final JwtService jwtService;
    private final SeekerRepository seekerRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void creatNewRolePermission(CreateRolePermissionRequest create) {
        String uuid = jwtService.extractUuid();


        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seeker not found!"
                )
        );
        if(roleRepository.existsByName(TextUtil.toUppercase(create.roleName()))){
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Role already exists!"
            );
        }

        Role role = Role.builder()
                .name(TextUtil.toUppercase(create.roleName()))
                .alias(SlugUtil.toSlug(create.roleName()))
                .uuid(UuidUtil.generateUuid(create.roleName()))
                .isDeleted(false)
                .createdAt(LocalDate.now())
                .build();
        roleRepository.save(role);


        List<RolePermission> rolePermissions = create.permissionIds().stream()
                .map(permissionId ->{
                    Permission permission = permissionRepository.findById(permissionId)
                            .orElseThrow(()-> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Permission with id " + permissionId + " not found!"
                            ));
                    return RolePermission.builder()
                            .role(role)
                            .permission(permission)
                            .createBy(seeker.getFullName())
                            .createdDate(LocalDateTime.now())
                            .build();
                }).toList();
        rolePermissionRepository.saveAll(rolePermissions);
    }

}
