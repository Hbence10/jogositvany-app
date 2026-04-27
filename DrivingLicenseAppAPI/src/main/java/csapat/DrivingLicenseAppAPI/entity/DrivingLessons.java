package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "driving_lesson")
@Getter
@Setter
@NoArgsConstructor
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllDrivingLesson", procedureName = "getAllDrivingLesson", resultClasses = DrivingLessons.class),
        @NamedStoredProcedureQuery(name = "getDrivingLesson", procedureName = "getDrivingLesson", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLessons.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLesson", procedureName = "deleteDrivingLesson", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }),
        @NamedStoredProcedureQuery(name = "getDrivingLessonBetweenHour", procedureName = "getDrivingLessonBetweenHour", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = Date.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "startHourIN", type = Date.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endHourIN", type = Date.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "instructorIDIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Long.class),
        @NamedStoredProcedureQuery(name = "getDrivingLessonByStudentId", procedureName = "getDrivingLessonByStudentId", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLessons.class)
})
public class DrivingLessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_km")
    @Null
    @Size(max = 7)
    private Integer startKm;

    @Column(name = "end_km")
    @Null
    @Size(max = 7)
    private Integer endKm;

    @Column(name = "location")
    @Null
    @Size(max = 100)
    private String location;

    @Column(name = "pick_up_place")
    @Null
    @Size(max = 100)
    private String pickUpPlace;

    @Column(name = "drop_off_place")
    @Null
    @Size(max = 100)
    private String dropOffPlace;

    @Column(name = "lesson_hour_number")
    @Null
    private Integer lessonHourNumber;

    @Column(name = "is_paid")
    @NotNull
    private Boolean isPaid = false;

    @Column(name = "is_end")
    @NotNull
    private Boolean isEnd = false;

    @Column(name = "is_cancelled")
    @NotNull
    private Boolean isCancelled = false;

    @Column(name = "cancelled_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelledAt;

    //Kapcsolatok:
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status drivingLessonStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "hour_id")
    private ReservedHour reservedHour;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Students dstudent;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Instructors dinstructor;
    //Constructorok:

    public DrivingLessons(ReservedHour reservedHour, Students dstudent, Instructors dinstructor, Status status) {
        this.reservedHour = reservedHour;
        this.dstudent = dstudent;
        this.dinstructor = dinstructor;
        this.drivingLessonStatus = status;
        this.lessonHourNumber = 0;
    }

    public DrivingLessons(Integer startKm, Integer endKm, String location, String pickupPlace, String dropOffPlace, Integer lessonHourNumber, Boolean isPaid, Boolean isEnd, ReservedHour reservedHour, Students dstudent, Instructors dinstructor, Status status) {
        this.startKm = startKm;
        this.endKm = endKm;
        this.location = location;
        this.pickUpPlace = pickupPlace;
        this.dropOffPlace = dropOffPlace;
        this.lessonHourNumber = lessonHourNumber;
        this.isPaid = isPaid;
        this.isEnd = isEnd;
        this.reservedHour = reservedHour;
        this.dstudent = dstudent;
        this.dinstructor = dinstructor;
        this.drivingLessonStatus = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartKm() {
        return startKm;
    }

    public void setStartKm(Integer startKm) {
        this.startKm = startKm;
    }

    public Integer getEndKm() {
        return endKm;
    }

    public void setEndKm(Integer endKm) {
        this.endKm = endKm;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPickUpPlace() {
        return pickUpPlace;
    }

    public void setPickUpPlace(String pickUpPlace) {
        this.pickUpPlace = pickUpPlace;
    }

    public String getDropOffPlace() {
        return dropOffPlace;
    }

    public void setDropOffPlace(String dropOffPlace) {
        this.dropOffPlace = dropOffPlace;
    }

    public Integer getLessonHourNumber() {
        return lessonHourNumber;
    }

    public void setLessonHourNumber(Integer lessonHourNumber) {
        this.lessonHourNumber = lessonHourNumber;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Status getDrivingLessonStatus() {
        return drivingLessonStatus;
    }

    public void setDrivingLessonStatus(Status drivingLessonStatus) {
        this.drivingLessonStatus = drivingLessonStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ReservedHour getReservedHour() {
        return reservedHour;
    }

    public void setReservedHour(ReservedHour reservedHour) {
        this.reservedHour = reservedHour;
    }

    public Students getDstudent() {
        return dstudent;
    }

    public void setDstudent(Students dstudent) {
        this.dstudent = dstudent;
    }

    public Instructors getDinstructor() {
        return dinstructor;
    }

    public void setDinstructor(Instructors dinstructor) {
        this.dinstructor = dinstructor;
    }
}
