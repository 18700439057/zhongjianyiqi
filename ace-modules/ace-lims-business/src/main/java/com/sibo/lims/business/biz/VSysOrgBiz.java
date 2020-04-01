package com.sibo.lims.business.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.sibo.lims.business.entity.VSysOrg;
import com.sibo.lims.business.entity.VSysUser;
import com.sibo.lims.business.feign.AdminFeign;
import com.sibo.lims.business.mapper.VSysOrgMapper;
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
public class VSysOrgBiz extends BusinessBiz<VSysOrgMapper,VSysOrg> {

    private Logger logger = LoggerFactory.getLogger(VSysUserBiz.class);

    @Autowired
    private AdminFeign adminFeign;

    public void queryDept(){
        Example example = new Example(VSysOrg.class);
        example.createCriteria().andEqualTo("isDel","0");
        List<VSysOrg> listSysOrg = super.selectByExample(example);
        List<Depart> listDepart = new ArrayList<>();
        listSysOrg.forEach(value ->{
            Depart depart = new Depart();
            depart.setId(value.getId());
            depart.setCode(value.getCode());
            depart.setCrtTime(new Date(value.getCreateTime().longValue()));
            depart.setCrtUserId(BaseContextHandler.getUserID());
            depart.setUpdTime(new Date());
            depart.setCrtUserName("lims同步");
            depart.setName(value.getName());
            depart.setParentId("root");
            listDepart.add(depart);
        });

        adminFeign.saveOrg(listDepart);
        logger.info("同步组织数据完成时间"+new Date(),listDepart);
    }
}