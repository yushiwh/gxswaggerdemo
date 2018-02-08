package com.jztey.demo.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jztey.demo.domain.User;
import com.jztey.framework.mvc.RestfulPagingResult;
import com.jztey.framework.mvc.RestfulResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/dubbo2")
@ResponseBody
public interface OrderServiceTwo {
	// 只为生成API用
	class RestfulResultUser extends RestfulResult<User> {
	}

	class RestfulPagingResultUser extends RestfulPagingResult<User> {
	}

	//////////////////////////////////////////////////////////
	//////////////////// 增加//////////////////////////////////
	//////////////////////////////////////////////////////////

	@ApiOperation(value = "添加User表数据", notes = "添加User表数据", response = RestfulResultUser.class)
	@RequestMapping(value = "/pt/users", method = RequestMethod.POST)
	@ApiImplicitParam(name = "user", value = "用户详细实体user", paramType = "body", required = true, dataType = "User")
	RestfulResult<User> insertOrUpdate(@RequestBody User user);

	//////////////////////////////////////////////////////////
	//////////////////// 删除//////////////////////////////////
	//////////////////////////////////////////////////////////

	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
	@ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	RestfulResult<User> delete(@PathVariable Long id);

	//////////////////////////////////////////////////////////
	//////////////////// GET方法提交查询//////////////////////
	//////////////////////////////////////////////////////////

	@ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
	@ApiImplicitParam(name = "id", value = "编号", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	RestfulResult<User> findBySexPathParam(@PathVariable Long id);
	
	
	
	@ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "user", value = "用户详细实体user", paramType = "body", required = true, dataType = "User") })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	RestfulResult<User> updateUser(@PathVariable Long id, @RequestBody User user);
	

	 
	///// mybatis的使用//////// 
	
	@ApiOperation(value = "mybatis请求获取用户详细信息", notes = "根据url的id来获取用户详细信息")
	@ApiImplicitParam(name = "id", value = "编号", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/mybatis/{id}", method = RequestMethod.GET)
	public RestfulResult<User> getMybatis(@PathVariable Long id) throws Exception;
	
	
	
	  //////dubbo的测试/////
	@ApiOperation(value = "dubbo请求测试", notes = "dubbo请求测试")	
	@RequestMapping(value = "/dubbo", method = RequestMethod.GET)
	public RestfulResult<String> getDubbo() throws Exception;
	
	
	
	//测试一下jpa
	@ApiOperation(value = "JPA请求测试", notes = "dubbo请求测试")	
	@RequestMapping(value = "/jpa", method = RequestMethod.GET)
	public RestfulResult<String> getjpa() throws Exception;

}
