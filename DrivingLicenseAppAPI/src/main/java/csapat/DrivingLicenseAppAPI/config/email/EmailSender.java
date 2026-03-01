package csapat.DrivingLicenseAppAPI.config.email;

import csapat.DrivingLicenseAppAPI.entity.InstructorJoinRequest;
import csapat.DrivingLicenseAppAPI.entity.SchoolJoinRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
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

    public void sendEmailAboutInstructorJoinRequestToInstructor(String toEmail, String instructorName, String studentName) throws MessagingException    {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setSubject("Csatlkozási kérelem!");
        helper.setTo(toEmail);

        Map<String, Object> emailObject = new HashMap<>();
        emailObject.put("instructorName", instructorName);
        emailObject.put("studentName", studentName);
        helper.setText(getHtmlBody("InstructorRequestInstructorTemplate.html", emailObject), true);
        mailSender.send(msg);
    }

    public void sendEmailAboutInstructorJoinRequestToStudent(String toEmail, InstructorJoinRequest request, String answer) throws  MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setSubject("Csatlkozási kérelem!");
        helper.setTo(toEmail);

        Map<String, Object> emailObject = new HashMap<>();
        emailObject.put("instructorName", request.getInstructorJoinRequestInstructor().getInstructorUser().getFirstName() + " " + request.getInstructorJoinRequestInstructor().getInstructorUser().getLastName());
        emailObject.put("studentName", request.getInstructorJoinRequestStudent().getStudentUser().getFirstName() + " " + request.getInstructorJoinRequestStudent().getStudentUser().getLastName());
        emailObject.put("answer", answer.equals("accept") ? "elfogadta" : "elutasitotta");
        emailObject.put("thirdPhrase", answer.equals("accept") ? "Mostantól elkezdhetsz vezeteni!" : "Próbálkozz később vagy keress fel egy másik oktatót");
        helper.setText(getHtmlBody("InstructorRequestStudentTemplate.html", emailObject), true);
        mailSender.send(msg);
    }

    public void sendEmailAboutSchoolRegistration(String toEmail) {
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

    public void sendEmailAboutSchoolJoinRequestToUser(SchoolJoinRequest request, String answer) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setSubject("Csatlakozási kérelem!");
        helper.setTo(request.getSchoolJoinRequestUser().getEmail());

        Map<String, Object> emailObject = new HashMap<>();
        emailObject.put("schoolName", request.getSchoolJoinRequestSchool().getName());
        emailObject.put("fullName", request.getSchoolJoinRequestUser().getFirstName() + " " + request.getSchoolJoinRequestUser().getLastName());
        emailObject.put("answer", answer.equals("accept") ? "elfogadta" : "elutasitotta");
        if (request.getSchoolJoinRequestUser().getRole().getName().equals("ROLE_instructor")) {
            emailObject.put("thirdPhrase", "Mostantól fogadhat diákot!");
        } else {
            emailObject.put("thirdPhrase", "Mostantól csatlakozhatsz oktatóhoz!");
        }

        helper.setText(getHtmlBody("SchoolRequestStudent.html", emailObject), true);
        mailSender.send(msg);
    }

    public void sendEmailAboutSchoolJoinRequestToSchool(String toEmail, SchoolJoinRequest newRequest) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setSubject("Csatlakozási kérelem!");
        helper.setTo(toEmail);

        Map<String, Object> emailObject = new HashMap<>();
        emailObject.put("schoolName", newRequest.getSchoolJoinRequestSchool().getName());
        emailObject.put("newMemberName", newRequest.getSchoolJoinRequestUser().getFirstName() + " " + newRequest.getSchoolJoinRequestUser().getLastName());
        emailObject.put("joinAs", newRequest.getSchoolJoinRequestUser().getRole().getName().equals("ROLE_instructor") ? "oktató" : "diák");
        helper.setText(getHtmlBody("SchoolRequestSchoolTemplate.html", emailObject), true);
        mailSender.send(msg);
    }

    private String getHtmlBody(String nameOfHtml, Map<String, Object> templateModel) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(nameOfHtml, thymeleafContext);
        return htmlBody;
    }
}
