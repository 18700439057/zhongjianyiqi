package com.sibo.lims.business.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.sibo.lims.business.entity.VSysAccount;
import com.sibo.lims.business.entity.VSysOrg;
import com.sibo.lims.business.feign.AdminFeign;
import com.sibo.lims.business.mapper.VSysAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-01-23 14:06:53
 */
@Service
@Transactional
public class VSysAccountBiz extends BusinessBiz<VSysAccountMapper,VSysAccount> {

    private Logger logger = LoggerFactory.getLogger(VSysUserBiz.class);

    @Autowired
    private AdminFeign adminFeign;

    public void queryAccount(){
        Example example = new Example(VSysAccount.class);
//        example.createCriteria().andNotEqualTo("orgId","40288076691f16a9016927b284c41941");
        List<VSysAccount> listAccount = super.selectByExample(example);
        adminFeign.saveUserAndDept(listAccount);
        logger.info("同步账户和部门关联数据完成"+new Date(),listAccount);
    }
}