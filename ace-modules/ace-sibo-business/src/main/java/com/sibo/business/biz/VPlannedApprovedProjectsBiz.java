package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VPlannedApprovedProjects;
import com.sibo.business.mapper.VPlannedApprovedProjectsMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 计量/核定项目表
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Service
@Transactional
public class VPlannedApprovedProjectsBiz extends BusinessBiz<VPlannedApprovedProjectsMapper,VPlannedApprovedProjects> {

    @Autowired
    private VPlannedApprovedProjectsMapper vPlannedApprovedProjectsMapper;
    @Override
    public void insertSelective(VPlannedApprovedProjects entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public void copyObj(String id){
        VPlannedApprovedProjects vPlannedApprovedProjects = vPlannedApprovedProjectsMapper.selectByPrimaryKey(id);
        if(null != vPlannedApprovedProjects){
            VPlannedApprovedProjects model = new VPlannedApprovedProjects();
            org.springframework.beans.BeanUtils.copyProperties(vPlannedApprovedProjects,model);
            model.setId(UUIDUtils.generateUuid());
            vPlannedApprovedProjectsMapper.insertSelective(model);
        }
    }
}