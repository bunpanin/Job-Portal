package job_portal.feature.role;

import job_portal.domain.backend.Role;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.role.dto.request.CreateRoleRequest;
import job_portal.feature.role.dto.request.UpdateRoleRequest;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.security.JwtService;
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
    private final JwtService jwtService;


    @Override
    public void deleteByUuid(String uuid) {

        String seekerUuid = jwtService.extractUuid();

        Seeker seeker = seekerRepository.findByUuid(seekerUuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seeker not found!"
                )
        );
        Role role = roleRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Role not found!"
                )
        );
        if(role.getIsDeleted() == true){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Role not found!"
            );
        }
        role.setIsDeleted(true);
        roleRepository.save(role);
    }

    @Override
    public void updateByUuid(String uuid, UpdateRoleRequest updateRoleRequest) {

        String seekerUuid = jwtService.extractUuid();

        Seeker seeker = seekerRepository.findByUuid(seekerUuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seeker not found!"
                )
        );
        Role role = roleRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Role not found!"
                )
        );
        if(role.getIsDeleted() == true){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Role not found!"
            );
        }
        role.setName(updateRoleRequest.name().toUpperCase().replace(" ", "_"));
        role.setUuid(UuidUtil.generateUuid(updateRoleRequest.name()));
        role.setAlias(SlugUtil.toSlug(updateRoleRequest.name()));
        roleRepository.save(role);
    }




    @Override
    public void createNewRole(CreateRoleRequest createRoleRequest) {

        String seekerUuid = jwtService.extractUuid();

        Seeker seeker = seekerRepository.findByUuid(seekerUuid).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seeker not found!"
                )
        );

        if(roleRepository.existsByName(createRoleRequest.name().toUpperCase().replace(" ", "_"))){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Role name already exists"
            );
        }

        Role role = Role.builder()
                .name(createRoleRequest.name().toUpperCase().replace(" ", "_"))
                .alias(SlugUtil.toSlug(createRoleRequest.name()))
                .uuid(UuidUtil.generateUuid(createRoleRequest.name()))
                .isDeleted(false)
                .createdAt(LocalDate.now())
                .build();
        roleRepository.save(role);
    }
}
