package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "vehicle")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "license_plate")
    @Size(max = 10)
    @NotNull
    private String licensePlate;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "type_id")
    private VehicleType vehicleType;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;

    @OneToOne(mappedBy = "vehicle", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Instructors instructor;

    //Constructorok
    public Vehicle(String licensePlate, String name) {
        this.licensePlate = licensePlate;
        this.name = name;
    }
}
