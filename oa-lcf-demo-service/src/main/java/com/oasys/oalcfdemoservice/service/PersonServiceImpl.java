package com.oasys.oalcfdemoservice.service;

import com.oasys.oalcfdemoapi.entity.Demo;
import com.oasys.oalcfdemoapi.service.PersonService;
import com.oasys.oalcfdemoservice.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    @Cacheable(value = "demo")
    public List<Demo> queryAll(@RequestBody Demo p) {
        return personDao.findAll(p,null);
    }

    //eg: 因不可抗拒的Bug唯一key问题，本配置文件无法实现对重载方法获取唯一key，若存在重载方法仅以第一次运行的方法值作为key的值，所以重载方法除第一个外自行设置key值
    //key值通过缓存该数据一次去Redis里面查key获得
    @Caching(evict = {@CacheEvict(value = "demo",key = "'demo::com.oasys.oalcfdemoservice.service.PersonServiceImpl.queryAll'")})
    public void clean(){}

}
