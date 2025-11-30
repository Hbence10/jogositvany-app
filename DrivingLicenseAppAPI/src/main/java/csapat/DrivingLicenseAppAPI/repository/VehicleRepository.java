package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Procedure(name = "getAllVehicle", procedureName = "getAllVehicle")
    List<Vehicle> getAllVehicle();

    @Procedure(name = "getVehicle", procedureName = "getVehicle")
    Vehicle getVehicle(@Param("idIN") Integer id);

    @Procedure(name = "deleteVehicle", procedureName = "deleteVehicle")
    String deleteVehicle(@Param("idIN") Integer id);
}
