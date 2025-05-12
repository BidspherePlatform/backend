package com.bidsphere.bidsphere.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender = new JavaMailSenderImpl();

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public void sendVerificationEmail(String toEmail, String userId, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Verify your BidSphere account");

        String verificationUrl = frontendUrl + "/verify-email?userId=" + userId + "&token=" + token;

        String emailContent =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>" +
                        "<h2 style='color: #1e40af;'>Welcome to BidSphere!</h2>" +
                        "<p>Thank you for registering. Please click the button below to verify your email address:</p>" +
                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + verificationUrl + "' style='background-color: #1e40af; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; display: inline-block;'>Verify Email</a>" +
                        "</div>" +
                        "<p>If the button doesn't work, you can also copy and paste the following link into your browser:</p>" +
                        "<p style='word-break: break-all;'><a href='" + verificationUrl + "'>" + verificationUrl + "</a></p>" +
                        "<p>This link will expire in 24 hours.</p>" +
                        "<p>If you did not create an account, please ignore this email.</p>" +
                        "<p>Best regards,<br>The BidSphere Team</p>" +
                        "</div>";

        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}