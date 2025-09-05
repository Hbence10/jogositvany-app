package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "fuel_types")
public class FuelType {

    private int id;
    private String name;

    private List<Vehicle> vehicles;
}
