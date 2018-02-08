package com.jztey.demo.dao.mapper1;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jztey.demo.domain.User;

@Repository
public interface UserMapper1 {
	List<User> findUserInfo(Long id);
}
