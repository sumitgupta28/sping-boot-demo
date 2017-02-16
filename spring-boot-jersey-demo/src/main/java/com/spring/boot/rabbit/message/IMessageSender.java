package com.spring.boot.rabbit.message;

import com.spring.boot.domain.User;

public interface IMessageSender {

	void sendMessage(User user);

}