package com.jztey.demo.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.jztey.demo.dao.CityDao;
import com.jztey.demo.domain.City;

/**
 * 城市业务逻辑实现类
 * <p>
 * Created by yushi on 07/02/2017.
 * 
 * @param <T>
 */
@Service
public class CityServiceImpl<T> extends Thread implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityDao             cityDao;

    @Autowired
    private RedisTemplate       redisTemplate;

    @Autowired
    private CacheTemplate<T>    cacheTemplate;

    /**
     * 获取城市逻辑： 如果缓存存在，从缓存中获取城市信息 如果缓存不存在，从 DB 中获取城市信息，然后插入缓存
     */
    public City findCityById(@PathVariable Long id) {

        // 从缓存中获取城市信息
        String key = "city_" + id;
        ValueOperations<String, City> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            City city = operations.get(key);

            LOGGER.info("CityServiceImpl.findCityById() : 从缓存中获取了城市 >> " + city.toString());
            return city;
        }

        // 从 DB 中获取城市信息
        City city = cityDao.findById(id);
       
        //人为增加休眠时间看到同时插入缓存的效果更加明显
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 插入缓存
        operations.set(key, city, 60, TimeUnit.SECONDS);
        LOGGER.info("CityServiceImpl.findCityById() : 城市插入缓存 >> " + city.toString());

        return city;
    }

    /**
     * 架构师级别代码 处理并发业务中出现缓存穿透的方法
     * 
     * @author yushi 2018-02-08
     */
    @Override
    public City findCityByIdByJGS(@PathVariable final Long id) {
        // 设置Key值
        String queryKey = "city_" + id;
        City result = cacheTemplate.getCache(queryKey, 60, TimeUnit.SECONDS, null, new CacheLoadable<City>() {

            @Override
            public City load() {
                City city = cityDao.findById(id);
                return city;
            }

        });
        return result;
    }

    @Override
    public Long saveCity(@RequestBody City city) {
        return cityDao.saveCity(city);
    }

    /**
     * 更新城市逻辑： 如果缓存存在，删除 如果缓存不存在，不操作
     */
    @Override
    public Long updateCity(@PathVariable Long id, @RequestBody City city) {
        Long ret = cityDao.updateCity(city);

        // 缓存存在，删除缓存
        String key = "city_" + city.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            LOGGER.info("CityServiceImpl.updateCity() : 从缓存中删除城市 >> " + city.toString());
        }

        return ret;
    }

    @Override
    public Long deleteCity(@PathVariable Long id) {

        Long ret = cityDao.deleteCity(id);

        // 缓存存在，删除缓存
        String key = "city_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            LOGGER.info("CityServiceImpl.deleteCity() : 从缓存中删除城市 ID >> " + id);
        }
        return ret;
    }

    /**
     * 注解的方法实现redis的缓存
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "citycache", keyGenerator = "yushiKeyGenerator")
    // @Cacheable(key = "#p0.toString()",value = "citycache" )
    public City findCityByIdZj(@PathVariable Long id) {
        LOGGER.info("无缓存的时候调用这里");
        // 从 DB 中获取城市信息
        City city = cityDao.findById(id);
        return city;
    }

    /**
     * 不同的value得到的不同的过期时间
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "othercitycache", keyGenerator = "yushiKeyGenerator")
    public City findCityByIdZjOther(@PathVariable Long id) {
        LOGGER.info("无缓存的时候调用这里");
        // 从 DB 中获取城市信息
        City city = cityDao.findById(id);
        return city;
    }

    @CachePut(value = "othercitycache", keyGenerator = "yushiKeyGenerator") // 更新othercitycache
                                                                            // 缓存
    @Override
    public Long updateCityZj(@PathVariable Long id, @RequestBody City city) {
        Long ret = cityDao.updateCity(city);
        LOGGER.info("更新缓存");
        return ret;
    }

    @CacheEvict(value = "othercitycache", keyGenerator = "yushiKeyGenerator") // 清空
                                                                              // othercitycache
                                                                              // 缓存
    @Override
    public Long deleteCityZj(@PathVariable Long id) {
        Long ret = cityDao.deleteCity(id);
        LOGGER.info("清除缓存" + id);
        return ret;
    }

    public String getcache1(final Long id) {

        final String key = "city_" + id;
        final ValueOperations<String, Long> operations = redisTemplate.opsForValue();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            LOGGER.info("走缓存 ");
            Long s = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            // 当时间超过10秒，异步更新数据到缓存
            if (s < 30) {
                new Thread() {

                    @Override
                    public void run() {
                        operations.set(key, id, 60, TimeUnit.SECONDS);
                        LOGGER.info("异步更新数据 ");
                    }
                }.start();

            }

        } else {
            // 插入缓存
            operations.set(key, id, 60, TimeUnit.SECONDS);
            LOGGER.info("插入缓存 >> ");

        }

        return "id:" + id;
    }

    @Override
    public String getcache(@PathVariable final Long id) {

        for (int i = 0; i < 5; i++) {
            new Thread() {

                @Override
                public void run() {
                    synchronized (id) {
                        getcache1(1L);
                    }

                }
            }.start();

        }

        return getcache1(id);

    }

}
