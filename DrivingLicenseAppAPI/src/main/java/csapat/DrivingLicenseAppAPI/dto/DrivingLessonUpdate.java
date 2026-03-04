package csapat.DrivingLicenseAppAPI.dto;

public record DrivingLessonUpdate(
        Integer startKm,
        Integer endKm,
        String location,
        String pickUpPlace,
        String dropOffPlace,
        Integer lessonHourNumber,
        Boolean isPaid,
        Long statusId,
        Long paymentMethodId
) {
}
