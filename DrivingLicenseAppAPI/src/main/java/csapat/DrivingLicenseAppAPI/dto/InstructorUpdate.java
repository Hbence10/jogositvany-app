package csapat.DrivingLicenseAppAPI.dto;

public record InstructorUpdate(
        String promoText,
        Long vehicleId,
        String vehicleName,
        String licensePlate,
        Long fuelTypeId,
        Long vehicleTypeId
) {
}
