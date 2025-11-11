package job_portal.domain.backend.seeker;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "work_experiences")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String jobTittle;

    @ManyToOne
    private JobLevel jobLevel;

    @ManyToOne
    private TypeOfExperience typeOfExperience;

    @Column(nullable = false, length = 100)
    private String companyName;
    @Column(nullable = false, length = 50)
    private String cityOrProvince;
    @Column(nullable = false, length = 50)
    private String country;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String descriptionsYourExperience;

    @OneToMany(mappedBy = "workExperience")
    private List<SeekerWorkExperience> seekerWorkExperiences;


}
