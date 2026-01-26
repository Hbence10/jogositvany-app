package csapat.DrivingLicenseAppAPI.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final String fromEmail = "ffeluton@gmail.com";

    public void sendVerificationCodeEmail(String toEmail, String verificationCode){

    }

    public void sendEmailAboutRegistration(String toEmail){
    }


}
