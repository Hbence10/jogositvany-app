package csapat.DrivingLicenseAppAPI.dto;

public record InstructorUpdate(
        String promoText,
        Integer vehicleId,
        String vehicleName,
        String licensePlate,
        Integer fuelTypeId,
        Integer vehicleTypeId
) {
}
