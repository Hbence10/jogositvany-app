package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllEducation", procedureName = "getAllEducation", resultClasses = Education.class),
        @NamedStoredProcedureQuery(name = "getEducation", procedureName = "getEducation", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Education.class)
})
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok
    @OneToMany(mappedBy = "userEducation", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Users> userEducationList;

    public Education(String name) {
        this.name = name;
    }

    public Education(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
