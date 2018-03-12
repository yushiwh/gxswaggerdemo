package com.jztey.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jztey.demo.dao.UserDao;
import com.jztey.demo.dao.mapper1.UserMapper1;
import com.jztey.demo.domain.MessageVo;
import com.jztey.demo.domain.User;
import com.jztey.demo.jms.Producer;
import com.jztey.demo.tools.OmronUtil;
import com.jztey.framework.mvc.BaseDao;
import com.jztey.framework.mvc.BaseService;
import com.jztey.framework.mvc.RestfulResult;
import com.qumaiyao.demo.service.DubboDemoService;

@Named
@Service
public class OrderServiceImplTwo extends BaseService<User> implements OrderServiceTwo {

    @Autowired
    private UserDao       userDao;

    @Autowired
    private UserMapper1   userMapper1;

    @Reference
    DubboDemoService      dubboDemoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Producer      producer;

    @Autowired
    private OmronUtil     omronUtil;

    @Override
    public BaseDao<User> getDao() {
        return userDao;
    }

    @Override
    @Transactional
    public RestfulResult<User> insertOrUpdate(@RequestBody User user) {
        User result = userDao.findByUniqueKey(user.getName());
        if (null == result) {
            userDao.persist(user);// 没有就增加
        } else {
            result.setId(user.getId());// 有就更新
            userDao.merge(result);
        }
        return new RestfulResult<>(user);
    }

    @Override
    @Transactional
    public RestfulResult<User> delete(@PathVariable Long id) {
        List<User> result = userDao.findByKeys(id);
        if (null != result) {
            for (User user : result) {
                this.remove(user.getId());// 进行批量删除
            }
            return new RestfulResult<>(result);
        }
        return new RestfulResult<>();
    }

    @Override
    @Transactional(readOnly = true)
    public RestfulResult<User> findBySexPathParam(@PathVariable Long id) {
        List<User> result = userDao.findByKeys(id);
        return new RestfulResult<>(result);
    }

    @Override
    public RestfulResult<User> getMybatis(@PathVariable Long id) throws Exception {
        List<User> result = userMapper1.findUserInfo(id);
        return new RestfulResult<>(result);
    }

    @Override
    @Transactional
    public RestfulResult<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        List<User> result = userDao.findByKeys(id);
        if (null != result && result.size() > 0) {
            user.setId(id);

            userDao.merge(user);
        }
        return new RestfulResult<>(user);

    }

    @Override
    public RestfulResult<String> getDubbo() throws Exception {
        String str = dubboDemoService.getName();
        return new RestfulResult<>(str);
    }

    @Override
    public RestfulResult<String> getjpa() throws Exception {

        List<User> list = new ArrayList<User>();
        // User user =null;
        for (int i = 0; i < 3; i++) {
            //
            User user = userDao.getJPA();

            System.out.println(user.getAge());
            user.setAge(user.getAge() + 1);
            System.out.println(user.getAge());

            // BeanUtils.copyProperties(user, usernew);//复制数据
            // list.add(usernew);
        }

        return new RestfulResult<>();
    }

    /**
     * 消息队列
     */
    @Override
    public RestfulResult<String> getactiveMq() throws Exception {
        RestfulResult<String> restfulResult = new RestfulResult<String>();

        //// 增加消息队列
        Destination destination = new ActiveMQQueue("test.queue");

        MessageVo mv = new MessageVo();
        mv.setDateReceive(omronUtil.dateChange(new Date()));
        mv.setDateSend(omronUtil.dateChange(new Date()));
        mv.setDeviceDigitalId(Math.random() * 1000 + "");
        mv.setOpenId(Math.random() * 1000 + "");
        mv.setUpdateAt(omronUtil.dateChange(new Date()));
        mv.setQueue("test.queue");
        mv.setState(0);
        mv.setMessage("测试队列");
        producer.sendMessage(destination, mv);
        restfulResult.setCode(0);
        restfulResult.setMessage("消息队列发送成功");
        restfulResult.setSuccess(true);

        return restfulResult;
    }

}
