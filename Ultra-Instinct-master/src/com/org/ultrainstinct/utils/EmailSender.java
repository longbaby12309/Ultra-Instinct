package com.org.ultrainstinct.utils;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class EmailSender {
    private static final String USERNAME = "kietdtps37203@fpt.edu.vn";
    private static final String PASSWORD = "rewa cwhq zfep hefk"; // Sử dụng mật khẩu ứng dụng
    public static void sendPasswordToUser(String recipientEmail, String newPassword) throws MessagingException {
        String subject = "Your New Password";
        String message = "Your new password is: " + newPassword;
        sendEmail(recipientEmail, subject, message);
    }
    public static void sendOtpToUser(String recipientEmail, String otp) throws MessagingException {
        String subject = "Your OTP Code";
        String message = "Your OTP code is: " + otp;
        sendEmail(recipientEmail, subject, message);
    }
    private static void sendEmail(String recipientEmail, String subject, String messageText) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setText(messageText);
        Transport.send(message);
    }
}
