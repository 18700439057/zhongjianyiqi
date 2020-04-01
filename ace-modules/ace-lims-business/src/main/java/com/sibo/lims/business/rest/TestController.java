package com.sibo.lims.business.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.sibo.lims.business.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CheckClientToken
@CheckUserToken
public class TestController {

    @Autowired
    private TestService testService;

   @GetMapping("/test")
    public void test(){
        String aaa = testService.test();
        System.out.println(aaa);
    }
}
