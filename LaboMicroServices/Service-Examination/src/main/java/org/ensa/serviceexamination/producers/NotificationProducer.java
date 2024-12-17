package org.ensa.serviceexamination.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ensa.serviceexamination.requests.EmailRequest;
import org.ensa.serviceexamination.requests.SmsRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(String recipient, String subject, String templateName, HashMap<String, String> variables) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        EmailRequest emailRequest = new EmailRequest(recipient, subject, templateName, variables);
        String jsonPayload = objectMapper.writeValueAsString(emailRequest); // Convert object to JSON string
        kafkaTemplate.send("email-topic", jsonPayload);
    }


    public void sendSms(String phoneNumber, String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
        String jsonPayload = objectMapper.writeValueAsString(smsRequest); // Convert object to JSON string
        kafkaTemplate.send("sms-topic", jsonPayload);
    }
}

