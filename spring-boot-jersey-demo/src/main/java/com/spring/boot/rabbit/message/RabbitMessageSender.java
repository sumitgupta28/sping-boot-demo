package com.spring.boot.rabbit.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import com.spring.boot.domain.User;

@Service
@Profile({ "cloud", "pcf" })
public class RabbitMessageSender implements IMessageSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/* (non-Javadoc)
	 * @see com.spring.boot.message.IMessageSender#sendMessage(com.spring.boot.domain.User)
	 */
	@Override
	public void sendMessage(User user) {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setAppId("User-Service");
		messageProperties.setCorrelationIdString(UUID.randomUUID().toString());
		Message message = new Message((user.getUserName() + "-" + user.getEmailAddress()).getBytes(),
				messageProperties);
		rabbitTemplate.sendAndReceive(message);
	}
}
