package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.DrivingLicenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrivingLicenseCategoryRepository extends JpaRepository<DrivingLicenseCategory, Integer> {
    @Procedure(name = "getAllDrivingLicenseCategory", procedureName = "getAllDrivingLicenseCategory")
    List<DrivingLicenseCategory> getAllDrivingLicenseCategory();

    @Procedure(name = "getDrivingLicenseCategory", procedureName = "getDrivingLicenseCategory")
    DrivingLicenseCategory getDrivingLicenseCategory(@Param("idIN") Integer id);

    @Procedure(name = "deleteDrivingLicenseCategory", procedureName = "deleteDrivingLicenseCategory")
    String deleteDrivingLicenseCategory(@Param("idIN") Integer id);
}
