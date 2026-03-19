package job_portal.domain;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role_permissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // MANY RolePermission → ONE Role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // MANY RolePermission → ONE Permission
    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;
}
