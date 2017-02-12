package com.spring.boot.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.spring.boot.domain.User;
import com.spring.boot.entity.UserTbl;

@Component
public class UserMapper {

	public UserTbl getUserTbl(User user) {
		UserTbl userTbl = new UserTbl();
		userTbl.setUserName(user.getUserName());
		userTbl.setEmailAddress(user.getEmailAddress());
		userTbl.setFirstName(user.getFirstName());
		userTbl.setLastName(user.getLastName());
		userTbl.setPhoneNumber(user.getPhoneNumber());
		userTbl.setAddress(user.getAddress());
		return userTbl;
	}

	public User getUser(UserTbl userTbl) {
		User user = new User();
		user.setUserName(userTbl.getUserName());
		user.setEmailAddress(userTbl.getEmailAddress());
		user.setFirstName(userTbl.getFirstName());
		user.setLastName(userTbl.getLastName());
		user.setPhoneNumber(userTbl.getPhoneNumber());
		user.setAddress(userTbl.getAddress());
		return user;
	}

	public List<User> getUserTbl(List<UserTbl> userTbls) {
		if (!CollectionUtils.isEmpty(userTbls)) {
			List<User> users = new ArrayList<>();
			for (UserTbl userTbl : userTbls) {
				users.add(getUser(userTbl));
			}
			return users;
		}
		return null;
	}
}
