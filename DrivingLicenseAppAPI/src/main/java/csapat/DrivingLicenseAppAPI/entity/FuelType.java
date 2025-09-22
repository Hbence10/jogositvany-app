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
@Table(name = "fuel_types")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(max = 11)
    private String name;

    //Kapcsolatok:
    @OneToMany(
            mappedBy = "fuelType",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Vehicle> vehicles;

    //Constructorok:
    public FuelType(String name) {
        this.name = name;
    }
}
