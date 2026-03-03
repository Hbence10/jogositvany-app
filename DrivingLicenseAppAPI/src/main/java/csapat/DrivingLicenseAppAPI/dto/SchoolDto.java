package csapat.DrivingLicenseAppAPI.dto;

public record SchoolDto(
        String schoolName,
        String email,
        String phoneNumber,
        String county,
        String town,
        String address,
        String promoText,
        Long ownerId
) {
}
