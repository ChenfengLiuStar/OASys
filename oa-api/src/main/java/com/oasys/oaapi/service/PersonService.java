package com.oasys.oaapi.service;

import com.oasys.oaapi.entity.Demo;
import com.oasys.oaapi.service.hystrix.PersonServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "oa-lcf-demo-service",fallback = PersonServiceHystrix.class)
public interface PersonService {
    @RequestMapping("/queryAll")
    public List<Demo> queryAll(@RequestBody Demo p);
}
