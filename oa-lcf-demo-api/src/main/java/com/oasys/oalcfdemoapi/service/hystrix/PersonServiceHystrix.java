package com.oasys.oalcfdemoapi.service.hystrix;

import com.oasys.oalcfdemoapi.entity.Demo;
import com.oasys.oalcfdemoapi.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//熔断器
@Component
public class PersonServiceHystrix implements PersonService {
    @Override
    public List<Demo> queryAll(Demo p) {
        System.out.println("查询失败");
        List<Demo> list = new ArrayList<>();
        return list;
    }
}
