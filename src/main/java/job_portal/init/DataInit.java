package job_portal.init;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import job_portal.domain.backend.Role;
import job_portal.domain.backend.RoleRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;

    @PostConstruct
    void init(){
        initRoleData();

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
    
}
