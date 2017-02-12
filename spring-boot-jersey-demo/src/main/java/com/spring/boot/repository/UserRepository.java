package com.spring.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.entity.UserTbl;

public interface UserRepository extends JpaRepository<UserTbl, String> {

	List<UserTbl> findByUserNameIgnoreCase(String userName);

	void deleteByUserNameIgnoreCase(String userName);

}
