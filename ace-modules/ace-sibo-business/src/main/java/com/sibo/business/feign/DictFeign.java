package com.sibo.business.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.sibo.business.vo.DictValue;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author ace
 * @create 2018/2/1.
 */
@FeignClient(value = "ace-dict",configuration = FeignApplyConfiguration.class)
public interface DictFeign {
    /**
     * 获取字典对对应值
     * @param code
     * @return
     */
    @RequestMapping(value = "/dictValue/feign/{code}",method = RequestMethod.GET)
    public Map<String,String> getDictValues(@PathVariable("code") String code);

    @RequestMapping(value = "/dictValue/all",method = RequestMethod.GET)
    public List<DictValue> getDictValuesAll();
}
