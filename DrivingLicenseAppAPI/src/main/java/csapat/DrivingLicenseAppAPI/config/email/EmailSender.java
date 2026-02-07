package csapat.DrivingLicenseAppAPI.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Component
@RequiredArgsConstructor
//@PropertySource("./application.properties")
public class EmailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCodeEmail(String toEmail, String verificationCode){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setText("adsads");
        mailSender.send(msg);
    }

    public void sendEmailAboutRegistration(String toEmail){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setText("adsads");
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

    public void sendEmailAboutPasswordReset(String toEmail) {

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
