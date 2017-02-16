package com.spring.boot.config.cloud;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.messaging.RabbitConnectionFactoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.spring.boot.common.UserServiceConstants;
import com.spring.boot.rabbit.message.RebbitMessageReceiver;

@Configuration
@Profile({ "cloud", "pcf" })
public class CloudFoundryRebbitMQConfig extends AbstractCloudConfig {

	@Bean
	public ConnectionFactory rabbitFactory() {
		Map<String, Object> properties = new HashMap<String, Object>();
		RabbitConnectionFactoryConfig rabbitConfig = new RabbitConnectionFactoryConfig(properties);
		return connectionFactory().rabbitConnectionFactory(UserServiceConstants.USER_MQ_SERVICE, rabbitConfig);
	}

	@Bean
	DirectExchange rubeExchange() {
		return new DirectExchange(UserServiceConstants.USER_QUEUE_SERVICE_EXCHANGE, true, false);
	}

	@Bean
	public Queue rubeQueue() {
		return new Queue(UserServiceConstants.USER_QUEUE_SERVICE_QUEUE, true);
	}

	@Bean
	Binding rubeExchangeBinding(DirectExchange rubeExchange, Queue rubeQueue) {
		return BindingBuilder.bind(rubeQueue).to(rubeExchange).with(UserServiceConstants.USER_QUEUE_SERVICE_KEY);
	}

	@Bean
	public RabbitTemplate rubeExchangeTemplate() {
		RabbitTemplate r = new RabbitTemplate(rabbitFactory());
		r.setExchange(UserServiceConstants.USER_QUEUE_SERVICE_EXCHANGE);
		r.setRoutingKey(UserServiceConstants.USER_QUEUE_SERVICE_KEY);
		r.setConnectionFactory(rabbitFactory());
		return r;
	}

	@Bean
	public RabbitMessagingTemplate rabbitMessagingTemplate() {
		return new RabbitMessagingTemplate(rubeExchangeTemplate());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(rabbitFactory());
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(UserServiceConstants.USER_QUEUE_SERVICE_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(RebbitMessageReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
