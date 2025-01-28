package dasturlash.uz.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationEmail(String email, Long profileId) {
        String subject = "Registration Confirmation";
        String body = "<div style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>"
                + "<h2 style='color: #4CAF50;'>Welcome to Our Service!</h2>"
                + "<p>Thank you for registering with us. To complete your registration, please verify your email address by clicking the link below:</p>"
                + "<p><a href='http://localhost:8080/auth/registration/verification/" + profileId
                + "' style='color: #4CAF50; text-decoration: none;'>Verify Email Address</a></p>"
                + "<p>If you did not request this registration, please ignore this email.</p>"
                + "<p>Best regards,<br>Your Company Team</p>"
                + "</div>";
        sendMimeEmail(email, subject, body);
    }

    public void sendMimeEmail(String email, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromAccount);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
            System.out.println("Email muvaffaqiyatli yuborildi.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
