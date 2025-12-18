package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Procedure(name = "getAllPaymentMethod", procedureName = "getAllPaymentMethod")
    List<PaymentMethod> getAllPaymentMethod();

    @Procedure(name = "getPaymentMethod", procedureName = "getPaymentMethod")
    Optional<PaymentMethod> getPaymentMethod(@Param("idIN") Integer id);

    @Procedure(name = "deletePaymentMethod", procedureName = "deletePaymentMethod")
    String deletePaymentMethod(@Param("idIN") Integer id);
}
