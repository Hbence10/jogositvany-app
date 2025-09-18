package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "driving_license_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrivingLicenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "min_age")
    @NotNull
    @Size(max = 2)
    private int minAge;

    //Kapcsolatok:
    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<DrivingLessons> drivingLessonsList;

    //Constructorok:
    public DrivingLicenseCategory(String name, int minAge) {
        this.name = name;
        this.minAge = minAge;
    }
}
