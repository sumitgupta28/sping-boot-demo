package com.spring.boot.config.cloud;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.common.RedisServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.spring.boot.common.UserServiceConstants;
import com.spring.boot.redis.message.RedisMessageReceiver;

@Configuration
@Profile({ "cloud", "pcf" })
public class CloudFoundryRedisConfig extends AbstractCloudConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisServiceInfo serviceInfo = (RedisServiceInfo) cloud()
				.getServiceInfo(UserServiceConstants.USER_REDIS_SERVICE);
		String serviceID = serviceInfo.getId();
		return cloud().getServiceConnector(serviceID, RedisConnectionFactory.class, null);
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		return new StringRedisTemplate(redisConnectionFactory());
	}

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	public RedisMessageReceiver receiver() {
		return new RedisMessageReceiver();
	}
}
