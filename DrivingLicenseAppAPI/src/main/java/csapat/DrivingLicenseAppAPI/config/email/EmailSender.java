package csapat.DrivingLicenseAppAPI.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final String fromEmail = "acsapat25@gmail.com";

    public void sendVerificationCodeEmail(String toEmail, String verificationCode){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.fromEmail);
        message.setSubject("Hitelesitő kód");
        message.setText("A hitelesitő kódja a jelszó változtatáshoz: " + verificationCode + ". \n A kód 5 percig aktív");
        message.setTo(toEmail);

        mailSender.send(message);
    }

    public void sendEmailAboutRegistration(String toEmail){
    }

    public void sendEmailAboutPasswordReset(String toEmail){
    }

    public void sendEmailAboutDrivingLesson(String toEmail){
    }

    public void sendEmailAboutDrivingLessonCancel(String toEmail){
    }

    public void sendEmailAboutInstructorRequest(String toEmail){
    }

    public void sendEmailAboutStudentRequest(String toEmail){
    }
}
