package com.BookStore.ServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.BookStore.Service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsServiceImpl implements SmsService {

	@Value("${twilio.account.sid}")
	private String accountSid;

	@Value("${twilio.auth.token}")
	private String authToken;

	@Value("${twilio.phone.number}")
	private String fromPhoneNumber;

	public void SmsService() {
	        Twilio.init(accountSid, authToken);
	    }

	@Override
	public void sendSms(String toPhoneNumber, String message) {
		System.out.println("ToPhoneNumber " + toPhoneNumber);
		System.out.println("fromPhoneNumber " + fromPhoneNumber);
		Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromPhoneNumber), message).create();
	}
}
