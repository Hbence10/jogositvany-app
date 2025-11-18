package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties({"vehicleList"})
    private VehicleType vehicleType;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "fuel_type_id")
    @JsonIgnoreProperties({"vehicles"})
    private FuelType fuelType;

    @OneToOne(mappedBy = "vehicle", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Instructors instructor;

    //Constructorok
    public Vehicle(String licensePlate, String name) {
        this.licensePlate = licensePlate;
        this.name = name;
    }
}
