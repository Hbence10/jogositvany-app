package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
    @OneToMany(
            mappedBy = "userEducation",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    @JsonIgnore
    private List<User> userEducationList;


    public Education(String name) {
        this.name = name;
    }
}
