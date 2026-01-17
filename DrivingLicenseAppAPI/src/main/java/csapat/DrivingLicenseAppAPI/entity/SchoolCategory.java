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
    private Integer id;

    @Column(name = "hourly_rate")
    @NotNull
    private Integer hourlyRate;

    //Kapcsolatok:
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "driving_license_category_id")
    private DrivingLicenseCategory licenseCategory;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School schoolCategory;
}
