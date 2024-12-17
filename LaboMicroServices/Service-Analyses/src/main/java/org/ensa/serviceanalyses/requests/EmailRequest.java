package org.ensa.serviceanalyses.requests;

import lombok.Data;

@Data
public class EmailRequest {
    private String recipient;
    private String subject;
    private String message;

    public EmailRequest() {}

    public EmailRequest(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

}
