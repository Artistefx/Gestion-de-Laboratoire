package org.ensa.servicemessagerie.services;


import org.ensa.servicemessagerie.requests.SmsRequest;

public interface SmsService {

    void sendSms(SmsRequest smsRequest);
}
