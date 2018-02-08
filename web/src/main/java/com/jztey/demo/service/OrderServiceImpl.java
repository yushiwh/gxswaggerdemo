package com.jztey.demo.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.jztey.demo.domain.User;
import com.jztey.framework.mvc.RestfulResult;

@com.alibaba.dubbo.config.annotation.Service
@Named
@Service
public class OrderServiceImpl implements OrderService {
	static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

	@Override
	public RestfulResult<User> select() {
		User user = new User();
		user.setId(1L);
		user.setName("刘林");
		user.setSex(1);
		return new RestfulResult<>(user);
	}

	@Override
	public RestfulResult<User> createUser(@RequestBody User user) {

		return new RestfulResult<>(user);
	}

	@Override
	public RestfulResult<String> deleteUser(@PathVariable Long id) {

		return new RestfulResult<>("OK：" + id);
	}

	@Override
	public RestfulResult<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		return new RestfulResult<>(user);
	}

	@Override
	public RestfulResult<User> selectById(@PathVariable Long id) {
		User user = new User();
		user.setId(id);
		return new RestfulResult<>(user);
	}

}
