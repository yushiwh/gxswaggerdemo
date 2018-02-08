package com.jztey.demo.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jztey.demo.domain.City;
import com.jztey.demo.domain.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 城市业务逻辑接口类
 * <p>
 * Created by yushi on 07/02/2017.
 */
@RequestMapping("/city")

@ResponseBody
public interface CityService {
	/**
	 * 根据城市 ID,查询城市信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据城市 ID,查询城市信息", notes = "根据城市 ID,查询城市信息")
	@ApiImplicitParam(name = "id", value = "城市ID", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	City findCityById(@PathVariable Long id);
	
	
	
	/**
     * 根据城市 ID,查询城市信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据城市 ID,查询城市信息——架构师级别的代码", notes = "架构师级别的代码")
    @ApiImplicitParam(name = "id", value = "城市ID", paramType = "path", required = true, dataType = "Long")
    @RequestMapping(value = "/jgs/{id}", method = RequestMethod.GET)
    City findCityByIdByJGS(@PathVariable  Long id);
	

	/**
	 * 新增城市信息
	 *
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "新增城市", notes = "新增城市")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiImplicitParam(name = "city", value = "城市实体city", paramType = "body", required = true, dataType = "City")

	Long saveCity(@RequestBody City city);

	/**
	 * 更新城市信息
	 *
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "更新城市详细信息", notes = "根据url的id来指定更新对象，并根据传过来的city信息来更新城市详细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "城市ID", paramType = "path", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "city", value = "城市实体user", paramType = "body", required = true, dataType = "City") })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	Long updateCity(@PathVariable Long id, @RequestBody City city);

	/**
	 * 根据城市 ID,删除城市信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除城市", notes = "根据url的id来指定删除对象")
	@ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Long deleteCity(@PathVariable Long id);

	/**
	 * 注解的方法
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "注解方法来查询缓存信息", notes = "根据城市 ID,查询城市信息")
	@ApiImplicitParam(name = "id", value = "城市ID", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/zj/{id}", method = RequestMethod.GET)
	City findCityByIdZj(@PathVariable Long id);

	@ApiOperation(value = "另外一种注解方法来查询缓存信息", notes = "根据城市 ID,查询城市信息")
	@ApiImplicitParam(name = "id", value = "城市ID", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/zjother/{id}", method = RequestMethod.GET)
	City findCityByIdZjOther(@PathVariable Long id);

	/**
	 * 更新城市信息
	 *
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "注解缓存方法更新城市详细信息", notes = "根据url的id来指定更新对象，并根据传过来的city信息来更新城市详细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "城市ID", paramType = "path", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "city", value = "城市实体user", paramType = "body", required = true, dataType = "City") })
	@RequestMapping(value = "/zj/{id}", method = RequestMethod.PUT)
	Long updateCityZj(@PathVariable Long id, @RequestBody City city);

	@ApiOperation(value = "注解缓存删除城市", notes = "根据url的id来指定删除对象")
	@ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "Long")
	@RequestMapping(value = "/zj/{id}", method = RequestMethod.DELETE)
	Long deleteCityZj(@PathVariable Long id);
	
	
	 
	@RequestMapping(value = "/getcache/{id}", method = RequestMethod.GET)
	String getcache(@PathVariable Long id);
	
	
}
