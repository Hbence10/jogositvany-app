package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "vehicle")
@Entity
public class Vehicle {

    private int id;
    private String licensePlate;
    private String name;

    private VehicleType vehicleType;
    private FuelType fuelType;
}
