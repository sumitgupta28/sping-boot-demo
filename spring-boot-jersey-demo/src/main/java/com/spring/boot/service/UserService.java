package com.spring.boot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.spring.boot.domain.User;
import com.spring.boot.entity.UserTbl;
import com.spring.boot.mapper.UserMapper;
import com.spring.boot.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public List<User> getAllUsers() {
		final List<UserTbl> userTbls = userRepository.findAll();
		return userMapper.getUserTbl(userTbls);
	}

	public List<User> getUserByUserName(final String userName) {
		final List<UserTbl> userTbls = userRepository.findByUserNameIgnoreCase(userName);
		return userMapper.getUserTbl(userTbls);
	}

	/**
	 * Create a user and returns true if user created Successfully else return
	 * false.
	 * 
	 * @param user
	 * @return {@link Boolean}
	 */
	@Transactional
	public boolean createUser(final User user) {
		boolean isExistingUser = !isExistingUser(user);
		if (isExistingUser) {
			final UserTbl userTbl = userMapper.getUserTbl(user);
			userRepository.save(userTbl);
		}
		return isExistingUser;
	}

	@Transactional
	public void updateUser(final User user) {
		if (isExistingUser(user)) {
			final UserTbl userTbl = userMapper.getUserTbl(user);
			userRepository.save(userTbl);
		} else {
			///
		}
	}

	@Transactional
	public void deleteUserByUserName(final String userName) {
		if (isExistingUser(userName)) {
			userRepository.deleteByUserNameIgnoreCase(userName);
		} else {
			///
		}
	}

	@Transactional
	public void deleteAllUser() {
		userRepository.deleteAll();
	}

	private Boolean isExistingUser(final String userName) {
		List<User> users = getUserByUserName(userName);
		return !CollectionUtils.isEmpty(users) ? true : false;
	}

	private Boolean isExistingUser(final User user) {
		List<User> users = getUserByUserName(user.getUserName());
		return !CollectionUtils.isEmpty(users) ? true : false;
	}
}
