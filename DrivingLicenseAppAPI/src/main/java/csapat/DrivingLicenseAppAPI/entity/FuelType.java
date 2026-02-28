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
@Table(name = "fuel_type")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllFuelType", procedureName = "getAllFuelType", resultClasses = FuelType.class),
        @NamedStoredProcedureQuery(name = "getFuelType", procedureName = "getFuelType", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = FuelType.class)
})
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 11)
    private String name;

    //Kapcsolatok:
    @OneToMany(mappedBy = "fuelType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehicle> vehicles;

    //Constructorok:
    public FuelType(String name) {
        this.name = name;
    }
}
