package org.ensa.servicemessagerie.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

import java.util.HashMap;

public class EmailRequest {
    private String recipient;
    private String subject;
    private String templateName;
    private HashMap<String, String> variables;

    public EmailRequest(String recipient, String subject, String templateName, HashMap<String, String> variables) {
        this.recipient = recipient;
        this.subject = subject;
        this.templateName = templateName;
        this.variables = variables;
    }

    public EmailRequest(){};

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public HashMap<String, String> getVariables() {
        return variables;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setVariables(HashMap<String, String> variables) {
        this.variables = variables;
    }
}
