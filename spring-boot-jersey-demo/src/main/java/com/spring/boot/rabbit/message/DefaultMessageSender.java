package com.spring.boot.rabbit.message;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.spring.boot.domain.User;


@Service
@Profile({ "default", "local" })
public class DefaultMessageSender implements IMessageSender {

	@Override
	public void sendMessage(User user) {
		System.out.println("Sending Message For User" + user.getEmailAddress());

	}

}
