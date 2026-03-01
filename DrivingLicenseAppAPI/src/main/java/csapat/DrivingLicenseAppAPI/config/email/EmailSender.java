package csapat.DrivingLicenseAppAPI.config.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
//@PropertySource("./application.properties")
public class EmailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCodeEmail(String toEmail, String fullName, String verificationCode) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setSubject("Hitelesitő kód");
        helper.setTo(toEmail);

        Map<String, Object> emailObject = new HashMap<>();
        emailObject.put("fullName", fullName);
        emailObject.put("vCode", verificationCode);
        helper.setText(getHtmlBody("VCodeTemplate.html", emailObject), true);
        mailSender.send(msg);
    }

    public void sendEmailAboutRegistration(String toEmail, String newUsersName) throws MessagingException {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setSubject("Sikeres regisztráció!");
            helper.setTo(toEmail);

            Map<String, Object> emailObject = new HashMap<>();
            emailObject.put("fullName", newUsersName);
            helper.setText(getHtmlBody("RegistrationTemplate.html", emailObject), true);
            mailSender.send(msg);
    }

    public void sendEmailAboutDrivingLessonCanceled(String toEmail) {
    }

    public void sendEmailAboutDrivingLessonRequestToInstructor(String toEmail) {
    }

    public void sendEmailAboutDrivingLessonRequestToStudent(String toEmail) {

    }

    public void sendEmailAboutInstructorJoinRequestToInstructor(String toEmail) {

    }

    public void sendEmailAboutInstructorJoinRequestToStudent(String toEmail) {

    }

    public void sendEmailAboutPasswordReset(String toEmail, String fullName) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setSubject("Sikeres Jelszó Frissités!");
        helper.setTo(toEmail);

        Map<String, Object> emailObject = new HashMap<>();
        emailObject.put("fullName", fullName);
        emailObject.put("updateDate", LocalDateTime.now());
        helper.setText(getHtmlBody("PasswordResetTemplate.html", emailObject), true);
        mailSender.send(msg);
    }

    public void sendEmailAboutSchoolJoinRequestToSchool(String toEmail) {

    }

    public void sendEmailAboutSchoolJoinRequestToUser(String toEmail) {

    }

    public void sendEmailAboutSchoolRegistration(String toEmail) {

    }

    private String getHtmlBody(String nameOfHtml, Map<String, Object> templateModel) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(nameOfHtml, thymeleafContext);
        return htmlBody;
    }
}
