package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.OpeningDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpeningDetailRepository extends JpaRepository<OpeningDetails, Integer> {

    @Procedure(name = "getAllOpeningDetail", procedureName = "getAllOpeningDetail")
    List<OpeningDetails> getAllOpeningDetail();

    @Procedure(name = "getOpeningDetail", procedureName = "getOpeningDetail")
    Optional<OpeningDetails> getOpeningDetail(@Param("idIN") Integer id);

    @Procedure(name = "deleteOpeningDetail", procedureName = "deleteOpeningDetail")
    String deleteOpeningDetail(@Param("idIN") Integer id);
}
