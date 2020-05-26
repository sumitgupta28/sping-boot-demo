package com.spring.boot.rabbit.message;

import org.springframework.stereotype.Component;

@Component
public class RebbitMessageReceiver {

	
	public void receiveMessage(String message) {
		System.out.println("[RebbitMessageReceiver] Message Received << " + message + ">>");
	}

}
