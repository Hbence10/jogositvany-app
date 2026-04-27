package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "payment_method")
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllPaymentMethod", procedureName = "getAllPaymentMethod", resultClasses = PaymentMethod.class),
        @NamedStoredProcedureQuery(name = "getPaymentMethod", procedureName = "getPaymentMethod", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = PaymentMethod.class)
})
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(max = 100)
    @NotNull
    private String name;

    //Kapcsolatok:
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DrivingLessons> drivingLessonsList;

    //Constructorok:
    public PaymentMethod(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DrivingLessons> getDrivingLessonsList() {
        return drivingLessonsList;
    }

    public void setDrivingLessonsList(List<DrivingLessons> drivingLessonsList) {
        this.drivingLessonsList = drivingLessonsList;
    }
}
