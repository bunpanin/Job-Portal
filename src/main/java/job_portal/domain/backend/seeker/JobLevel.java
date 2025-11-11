package job_portal.domain.backend.seeker;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "job_levels")
public class JobLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 50)
    private String alias;
    @Column(nullable = false, unique = true, length = 25)
    private String name; 
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    private Boolean isDeleted;
    private LocalDate createdAt;

    // Reletionship Table

    @OneToMany(mappedBy = "jobLevel")
    private List<Seeker> seekers;

    @OneToMany(mappedBy = "jobLevel")
    private List<WorkExperience> workExperiences;




    
}
