package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "vehicle")
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "license_path")
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

    @OneToOne(mappedBy = "vehicle", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private Instructors instructor;

    //Constructorok
    public Vehicle() {
    }

    public Vehicle(String licensePlate, String name) {
        this.licensePlate = licensePlate;
        this.name = name;
    }

    //Getterek & Setterek
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", name='" + name + '\'' +
                ", vehicleType=" + vehicleType +
                ", fuelType=" + fuelType +
                ", instructor=" + instructor +
                '}';
    }
}
