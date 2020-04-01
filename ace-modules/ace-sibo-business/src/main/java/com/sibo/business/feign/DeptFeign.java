package com.sibo.business.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.sibo.business.entity.Depart;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "ace-admin",configuration = FeignApplyConfiguration.class)
public interface DeptFeign {

    @RequestMapping(value = "/depart/getByUserId/{userId}",method = RequestMethod.GET)
    public Depart getDeptByUserId(@PathVariable("userId") String userId);


    @RequestMapping(value = "/depart/all",method = RequestMethod.GET)
    public List<Depart> getDeptAll();


}
