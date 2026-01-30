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
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "getAllRole", procedureName = "getAllRole", resultClasses = Role.class), @NamedStoredProcedureQuery(name = "getRole", procedureName = "getRole", parameters = {@StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)}, resultClasses = Users.class), @NamedStoredProcedureQuery(name = "deleteRole", procedureName = "deleteRole", parameters = {@StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)}, resultClasses = String.class)})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok:
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Users> userList;

    //Constructorok
    public Role(String name) {
        this.name = name;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
