package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "school_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SchoolCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hourly_rate")
    @NotNull
    private Integer hourlyRate;

    //Kapcsolatok:
    @ManyToOne
    @JoinColumn(name = "driving_license_category_id")
    private DrivingLicenseCategory licenseCategory;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School schoolCategory;

    public SchoolCategory(Integer hourlyRate, DrivingLicenseCategory licenseCategory, School schoolCategory) {
        this.hourlyRate = hourlyRate;
        this.licenseCategory = licenseCategory;
        this.schoolCategory = schoolCategory;
    }
}
