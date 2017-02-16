package com.spring.boot.redis.message;

import org.springframework.stereotype.Service;

@Service
public class RedisMessageReceiver {

	public void receiveMessage(String message) {
		System.out.println("[RedisMessageReceiver] Message Received << " + message + ">>");
	}

}
