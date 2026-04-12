package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Procedure(name = "getAllVehicle", procedureName = "getAllVehicle")
    List<Vehicle> getAllVehicle();

    @Procedure(name = "getVehicle", procedureName = "getVehicle")
    Optional<Vehicle> getVehicle(@Param("idIN") Long id);

    Optional<Vehicle> findByLicensePlateAndIsDeleted(String licensePlate, Boolean isDeleted);
}
