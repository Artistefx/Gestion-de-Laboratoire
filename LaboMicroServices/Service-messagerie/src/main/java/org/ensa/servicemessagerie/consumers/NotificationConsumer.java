package org.ensa.servicemessagerie.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ensa.servicemessagerie.requests.EmailRequest;
import org.ensa.servicemessagerie.requests.SmsRequest;
import org.ensa.servicemessagerie.services.EmailService;
import org.ensa.servicemessagerie.services.SmsService;
import org.ensa.servicemessagerie.twilio.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationConsumer {

    private final SmsService smsService;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public NotificationConsumer(SmsService smsService) {
        this.smsService = smsService;
    }

    @KafkaListener(topics = "email-topic", groupId = "messagerie-group")
    public void handleEmail(String jsonPayload) throws JsonProcessingException {
        System.out.println("Received JSON: " + jsonPayload);
        ObjectMapper objectMapper = new ObjectMapper();
        EmailRequest emailRequest = objectMapper.readValue(jsonPayload, EmailRequest.class); // Convert JSON string to object
        EmailService emailService = new EmailService(username,password);
        emailService.sendEmail(emailRequest.getRecipient(), emailRequest.getSubject(), emailRequest.getTemplateName(), emailRequest.getVariables());
        System.out.println("Processed email request: " + emailRequest);
    }

    @KafkaListener(topics = "sms-topic", groupId = "messagerie-group")
    public void handleSms(String jsonPayload) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        SmsRequest smsRequest = objectMapper.readValue(jsonPayload, SmsRequest.class); // Convert JSON string to object
        smsService.sendSms(smsRequest);
        System.out.println("Processed sms request: " + smsRequest);
    }
}

