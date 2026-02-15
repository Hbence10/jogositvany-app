package csapat.DrivingLicenseAppAPI.dto;

public record SchoolRegisterDto(
        String schoolName,
        String email,
        String phoneNumber,
        String county,
        String town,
        String address,
        String promoText,
        Integer ownerId

) {
}
