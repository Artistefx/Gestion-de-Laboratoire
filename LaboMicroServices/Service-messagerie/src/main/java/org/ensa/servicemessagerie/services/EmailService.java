package org.ensa.servicemessagerie.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;

public class EmailService {

    private final String username;
    private final String password;

    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String recipient, String subject, String templateName, Map<String, String> variables) {
        // Configure SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server
        props.put("mail.smtp.port", "587"); // SMTP port
        props.put("mail.smtp.auth", "true"); // Authentication required
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // SSL trust

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Load the email template from resources
            String templateContent = loadTemplateContent(templateName);

            // Replace placeholders in the template with actual values
            String content = templateContent;
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sender's address
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient) // Recipient's address
            );
            message.setSubject(subject); // Email subject
            message.setContent(content, "text/html"); // Email content as HTML

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            System.err.println("Failed to send email.");
        }
    }

    private String loadTemplateContent(String templateName) throws IOException {
        // Load the template file from the resources/templates directory
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }

}