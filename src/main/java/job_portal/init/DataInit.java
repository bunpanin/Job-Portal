package job_portal.init;
import java.util.ArrayList;
import java.util.List;

import job_portal.domain.Permission;
import job_portal.domain.Role;
import job_portal.domain.RolePermission;
import job_portal.feature.admin.Permission.PermissionRepository;
import job_portal.feature.admin.Role.RoleRepository;
import job_portal.feature.admin.RolePermission.RolePermissionRepository;
import job_portal.util.UserTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;


    @PostConstruct
    void init(){
        initRole();
        initPermission();
        initRolePermission();
    }
    private void initRolePermission(){
        List<RolePermission> rolePermissions = new ArrayList<>();

        Role roleSeeker = roleRepository.findById(1).orElseThrow(()-> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
                    "Role not found"
            )
        );
        Role roleCompany = roleRepository.findById(2).orElseThrow(()-> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
                    "Role not found"
            )
        );
        Role roleAdmin = roleRepository.findById(3).orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Role not found"
                )
        );

        Permission permissionAdmin = permissionRepository.findById(1).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Permission not found"
        ));
        Permission permissionSeeker = permissionRepository.findById(2).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Permission not found"
        ));
        Permission permissionCompany = permissionRepository.findById(3).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Permission not found"
        ));

        rolePermissions.add(RolePermission.builder()
                .role(roleSeeker)
                .permission(permissionSeeker)
                .build());
        rolePermissions.add(RolePermission.builder()
                .role(roleCompany)
                .permission(permissionCompany)
                .build());
        rolePermissions.add(RolePermission.builder()
                .role(roleAdmin)
                .permission(permissionAdmin)
                .build());

        rolePermissionRepository.saveAll(rolePermissions);

    }

    private void initPermission(){
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.builder().name("ADMIN_FULL_ACCESS").build());
        permissions.add(Permission.builder().name("SEEKER_MANAGE_PROFILE").build());
        permissions.add(Permission.builder().name("COMPANY_FULL_ACCESS").build());

        permissionRepository.saveAll(permissions);
    }

    private void initRole(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().name(UserTypeEnum.SEEKER.toString()).build());
        roles.add(Role.builder().name(UserTypeEnum.COMPANY.toString()).build());
        roles.add(Role.builder().name(UserTypeEnum.ADMIN.toString()).build());
        roleRepository.saveAll(roles);
    }
}