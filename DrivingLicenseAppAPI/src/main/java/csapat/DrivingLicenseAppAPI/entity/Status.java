package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "status")
@Getter
@Setter
@NoArgsConstructor
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllStatus", procedureName = "getAllStatus", resultClasses = Status.class),
        @NamedStoredProcedureQuery(name = "getStatus", procedureName = "getStatus", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Status.class)
})
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok:
    @OneToMany(
            mappedBy = "drivingLessonStatus",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<DrivingLessons> drivingLessonsList;

    //Constructorok:
    public Status(String name) {
        this.name = name;
    }
}
