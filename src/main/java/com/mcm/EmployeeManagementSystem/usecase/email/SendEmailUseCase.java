package com.mcm.EmployeeManagementSystem.usecase.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailUseCase {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;

    @Async
    public void send(String email, String message, String subject) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject(subject);
        mail.setText(message);
        javaMailSender.send(mail);

        System.out.println("Email sent!");
    }

    @Async
    public void sendQRCode(String email, String qrCodePath, String subject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setSubject(subject);

            // Zamena separatora u putanji qrCodePath
            String qrCodePathModified = qrCodePath.replace("\\", "/");
            FileSystemResource qrCodeFile = new FileSystemResource(qrCodePathModified);
            helper.addAttachment("QRCode.png", qrCodeFile);

            javaMailSender.send(message);

            System.out.println("Email with QR code sent!");
        } catch (MessagingException e) {
            e.printStackTrace();
            // Obrada izuzetka prilikom slanja poruke
        } catch (MailException e) {
            e.printStackTrace();
            // Obrada izuzetka prilikom slanja mejla
        }
    }



}
