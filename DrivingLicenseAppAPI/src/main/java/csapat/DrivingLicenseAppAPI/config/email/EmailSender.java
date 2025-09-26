package csapat.DrivingLicenseAppAPI.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final String fromEmail = "";

    public void sendVerificationCodeEmail(String email, String verificationCode){

    }


}
