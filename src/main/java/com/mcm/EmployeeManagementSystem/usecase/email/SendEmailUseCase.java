package com.mcm.EmployeeManagementSystem.usecase.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
@RequiredArgsConstructor
public class SendEmailUseCase {

    private final JavaMailSender javaMailSender;
    private final Environment env;

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

    public void sendQRCode(String toAddress, String subject, String message, String filePath) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
        helper.setTo(toAddress);
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setSubject(subject);
        helper.setText(message);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        helper.addAttachment("image.png", file);
        javaMailSender.send(helper.getMimeMessage());
    }
}
