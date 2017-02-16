package com.spring.boot.redis.message;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.spring.boot.domain.User;

@Service
@Profile({ "cloud", "pcf" })
public class RedisMessageSender implements IRedisMessageSender {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static final String KEY = "USER";
	private HashOperations<String, String, String> hashOps;

	@Override
	public void sendMessage(User user) {
		redisTemplate.convertAndSend("user", user.getUserName() + "-" + user.getEmailAddress());
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	public void saveUser(User user) {
		hashOps.put(KEY, user.getUserName(), user.toString());
	}

	public void updateUser(User user) {
		hashOps.put(KEY, user.getUserName(), user.toString());
	}

	public String findUser(String id) {
		return  hashOps.get(KEY, id);
	}

	public Map<String, String> findAllUsers() {
		return hashOps.entries(KEY);
	}

	public void deleteUser(String id) {
		hashOps.delete(KEY, id);
	}
	
	
}
