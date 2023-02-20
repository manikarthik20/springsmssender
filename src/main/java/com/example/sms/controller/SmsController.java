package com.example.sms.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.model.SmsPoja;
import com.example.sms.service.SMSService;

@RestController
public class SmsController {

	@Autowired
	SMSService service;

	@Autowired
	private SimpMessagingTemplate WebSocket;

	private final String TOPIC_DESTINATION = "/lesson/sms";

	@RequestMapping(value = "/sms", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void smsSubmit(@RequestBody SmsPoja sms) {
		try {
			service.send(sms);
		} catch (Exception e) {
			// TODO: handle exception
			WebSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: " + e.getMessage());
			throw e;
		}
		WebSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp() + ": SMS has been sent!"+sms.getTo());
	}                                                                           
//
//	@RequestMapping(value = "/smscallback",method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
//	public void smsCallback(@RequestBody MultiValueMap<String, String> map) {
//		service.receive(map);
//		WebSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp() + ":Twillo has made a callback request! Here are contents");
//	}
//	
	
	private String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}
}
