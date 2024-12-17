package org.ensa.servicemessagerie.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.ensa.servicemessagerie.requests.SmsRequest;
import org.ensa.servicemessagerie.services.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsSender implements SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.trial-number}")
    private String twilioPhoneNumber;

    // Constructor to initialize Twilio
    public TwilioSmsSender(@Value("${twilio.account-sid}") String accountSid,
                           @Value("${twilio.auth-token}") String authToken,
                           @Value("${twilio.trial-number}") String twilioPhoneNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.twilioPhoneNumber = twilioPhoneNumber;
        Twilio.init(accountSid, authToken);  // Initialize Twilio in the constructor
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        // Ensure that Twilio has been initialized
        if (accountSid == null || authToken == null || twilioPhoneNumber == null) {
            throw new IllegalStateException("Twilio credentials are not properly set.");
        }

        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioPhoneNumber);
        String body = smsRequest.getMessage();

        // Create and send the SMS
        Message message = Message.creator(to, from, body).create();
        System.out.println("Message sent with SID: " + message.getSid());
    }
}
