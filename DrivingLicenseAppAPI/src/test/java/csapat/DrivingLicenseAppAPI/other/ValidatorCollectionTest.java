package csapat.DrivingLicenseAppAPI.other;

import csapat.DrivingLicenseAppAPI.service.other.ValidatorCollection;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidatorCollectionTest {

    @Test
    @DisplayName("E-mail validator with valid e-mail")
    public void testEmailValidationWithValidInput() throws Exception {
        assertTrue(ValidatorCollection.emailValidator("test@gmail.com"), "test@gmail is valid email.");
    }

    @Test
    @DisplayName("E-mail validator with invalid e-mail (without .com)")
    public void testEmailValidationWithEmailWithoutCommercial() throws Exception {
        assertFalse(ValidatorCollection.emailValidator("test@gmail."), "test@gmail. isn't valid.");
    }

    @Test
    @DisplayName("E-mail validator with invalid e-mail (without @)")
    public void testEmailValidationWithEmailWithoutCommat() throws Exception {
        assertFalse(ValidatorCollection.emailValidator("testgmail.com"), "testgmail.com isn't valid.");
    }

    //Password tests:
    @Test
    @DisplayName("Password validator with valid password")
    public void testPasswordValidationWithValidPassword() throws Exception {
        assertTrue(ValidatorCollection.passwordValidator("test5.Asd"), "test5.Asd is valid password");
    }

    @Test
    @DisplayName("Password validator with short password (lesser character than 8 character)")
    public void testPasswordValidationWithShortPassword() throws Exception {
        assertFalse(ValidatorCollection.passwordValidator("t5.Asd"), "t5.Asd isn't valid, because it doesn't have 8 or more character.");
    }

    @Test
    @DisplayName("Password validator with long password (more character than 16 character)")
    public void testPasswordValidationWithLongPassword() throws Exception {
        assertFalse(ValidatorCollection.passwordValidator("testtesttesttesttest5.Asd"), "testtesttesttesttest5.Asd isn't valid because it has more character than 16.");
    }

    @Test
    @DisplayName("Password validator without special any special character")
    public void testPasswordValidationWithPasswordWithoutSpecialCharacter() throws Exception {
        assertFalse(ValidatorCollection.passwordValidator("test5Asd"), "test5Asd isn't valid because it doesn't contain any special character");
    }

    @Test
    @DisplayName("Password validator without special any lowercase character")
    public void testPasswordValidationWithPasswordWithoutLowerCaseCharacter() throws Exception {
        assertFalse(ValidatorCollection.passwordValidator("TEST5.ASD"), "test5Asd isn't valid because it doesn't contain any lowercase character");
    }

    @Test
    @DisplayName("Password validator without special any uppercase character")
    public void testPasswordValidationWithPasswordWithoutUpperCaseCharacter() throws Exception {
        assertFalse(ValidatorCollection.passwordValidator("TEST5.ASD"), "test5Asd isn't valid because it doesn't contain any uppercase character");
    }

    //Phone validator:
    @Test
    @DisplayName("Phone Validator with valid phone")
    public void testPhoneValidationWithValidPhoneNumber() throws Exception {
        assertTrue(ValidatorCollection.phoneValidator("06706285232"), "06706285232 is a valid phone number.");
    }

    @Test
    @DisplayName("Phone validator with wrong phone service code")
    public void testPhoneValidationWithWrongPhoneServiceCode() throws Exception {
        String phone = "06676285232";
        System.out.println(phone.substring(2,4));

        assertFalse(ValidatorCollection.phoneValidator("06676285232"), "06676285232 isn't valid phone number because 67 isn't valid phone service code.");
    }

    @Test
    @DisplayName("Phone validator with too long phonenumber")
    public void testPhoneValidationWthTooLongPhoneNumber() throws Exception {
        assertFalse(ValidatorCollection.phoneValidator("06706285232123"), "06706285232123 isn't valid phone number because it has more character than 11.");
    }
}
