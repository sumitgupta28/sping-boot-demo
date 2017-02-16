package com.spring.boot.redis.message;

import java.util.Map;

import com.spring.boot.domain.User;

public interface IRedisMessageSender {
	public void sendMessage(User user);
	
	public void saveUser(User user) ;

	public void updateUser(User user);

	public String findUser(String id) ;

	public Map<String, String> findAllUsers() ;

	public void deleteUser(String id) ;


}