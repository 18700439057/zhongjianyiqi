package com.sibo.business;

import com.github.wxiaoqi.merge.EnableAceMerge;
import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**zh
 * @author ace
 * @version 2017/12/26.
 */
@EnableEurekaClient
@SpringBootApplication
// 开启事务
//@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.sibo.business.feign"})
@MapperScan("com.sibo.business.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
@EnableAceMerge
public class BusinessBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(BusinessBootstrap.class).web(true).run(args);    }
}
