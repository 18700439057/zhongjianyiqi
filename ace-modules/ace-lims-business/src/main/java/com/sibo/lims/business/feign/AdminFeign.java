package com.sibo.lims.business.feign;

import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.sibo.lims.business.entity.VSysAccount;
import com.sibo.lims.business.entity.VSysUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "ace-admin",configuration = FeignApplyConfiguration.class)
public interface AdminFeign {

    @RequestMapping(value = "/user/save",method = RequestMethod.POST)
    public void saveUser(@RequestBody List<User> list);

    @RequestMapping(value = "/depart/save",method = RequestMethod.POST)
    public void saveOrg(@RequestBody List<Depart> list);

    @RequestMapping(value = "/user/relevancy",method = RequestMethod.POST)
    public void saveUserAndDept(@RequestBody List<VSysAccount> list);
}
