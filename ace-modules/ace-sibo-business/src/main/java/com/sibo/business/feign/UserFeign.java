package com.sibo.business.feign;

import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@FeignClient(value = "ace-admin",configuration = FeignApplyConfiguration.class)
public interface UserFeign {

    @RequestMapping(value = "/user/alltest", method = RequestMethod.GET)
    public List<User> getAllUser();

}
