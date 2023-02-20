package com.example.sms.service;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.example.sms.model.SmsPoja;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SMSService {

	private final String ACCOUNT_SID = "AC881887824d94e4ceb954ad4898301083";//ENTER YOUR SID NUMBER FROM TWILIO
	
	private final String AUTH_TOKEN = "50f2cab5181feac8e1c5fd1af798ef4e";//ENTER YOUR AUTH TOKEN  FROM TWILIO ACCOUNT
	
	private final String FROM_NUMBER = "+15104557817";//ENTER THE PHONE NUMBER GENERATED FROM TWILIO
	
	public void send(SmsPoja sms) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage()).create();
		
		System.out.println("here is my id : "+message.getSid());//unique resource ID created to manage this transaction
	}
	
	public void receive(MultiValueMap<String, String> smscallback) {
		
	}
}
