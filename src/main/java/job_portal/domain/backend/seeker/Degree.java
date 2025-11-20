package job_portal.domain.backend.seeker;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "degrees")
public class Degree {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(nullable = false, unique = true)
    private String alias;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private LocalDate createdAt;
    @Column(nullable = false)
    private Boolean isDeleted;
    

    
}
