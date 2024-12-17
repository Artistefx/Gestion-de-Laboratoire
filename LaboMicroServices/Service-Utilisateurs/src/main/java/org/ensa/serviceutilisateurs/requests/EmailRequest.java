package org.ensa.serviceutilisateurs.requests;

import lombok.Data;

import java.util.HashMap;

@Data
public class EmailRequest {
    private String recipient;
    private String subject;
    private  String templateName;
    private HashMap<String, String> variables;

    public EmailRequest() {}

    public EmailRequest(String recipient, String subject, String templateName, HashMap<String, String> variables) {
        this.recipient = recipient;
        this.subject = subject;
        this.templateName = templateName;
        this.variables = variables;
    }

}
