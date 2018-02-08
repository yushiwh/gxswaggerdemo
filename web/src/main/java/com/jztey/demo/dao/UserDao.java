package com.jztey.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.jztey.demo.domain.User;
import com.jztey.framework.mvc.BaseDao;

/**
 * Created by ys on 2016/8/31.
 */
@Repository
public class UserDao extends BaseDao<User> {

	public User findByUniqueKey(String name) {
		List<User> result = this.em.createQuery("select o from User o where o.name=:name", User.class)
				.setParameter("name", name).getResultList();
		return CollectionUtils.isEmpty(result) ? null : result.get(0);
	}

	public List<User> findByKeys(int age, int sex) {
		List<User> result = this.em.createQuery("select o from User o where o.sex=:sex and o.age=:age", User.class)
				.setParameter("sex", sex).setParameter("age", age).getResultList();
		return CollectionUtils.isEmpty(result) ? null : result;
	}
	
	public List<User> findByKeys(Long id) {
		List<User> result = this.em.createQuery("select o from User o where o.id=:id ", User.class)
				.setParameter("id", id).getResultList();
		return CollectionUtils.isEmpty(result) ? null : result;
	}

	public List<User> findByKey(int sex) {
		List<User> result = this.em.createQuery("select o from User o where o.sex=:sex  ", User.class)
				.setParameter("sex", sex).getResultList();
		return CollectionUtils.isEmpty(result) ? null : result;
	}

	
	public User getJPA() {
		 
		User result = (User)this.em.createQuery("select o from User o where 1=1 and o.name='zz'")
				.getSingleResult();
		this.em.clear();//加上这个才能去掉托管的状态，值才会变为空
		
		return   result;
	}
	
}
