package csapat.DrivingLicenseAppAPI.dto;

public record DrivingLessonUpdate(
        Integer startKm,
        Integer endKm,
        String location,
        String pickUpPlace,
        String dropOffPlace,
        Integer lessonHourNumber,
        Boolean isPaid,
        Integer statusId,
        Integer paymentMethodId
) {
}
