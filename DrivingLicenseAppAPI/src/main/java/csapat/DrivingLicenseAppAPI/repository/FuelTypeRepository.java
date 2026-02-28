package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    @Procedure(name = "getAllFuelType", procedureName = "getAllFuelType")
    List<FuelType> getAllFuelType();

    @Procedure(name = "getFuelType", procedureName = "getFuelType")
    Optional<FuelType> getFuelType(@Param("idIN") Long id);
}
