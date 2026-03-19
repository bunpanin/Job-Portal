package job_portal.domain;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name; // ADMIN, SEEKER, COMPANY

    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;


}