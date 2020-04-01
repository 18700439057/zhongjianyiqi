package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.vo.VMeasurementBasicVo;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMeasurementBasic;
import com.sibo.business.mapper.VMeasurementBasicMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-06 18:27:28
 */
@Service
@Transactional
public class VMeasurementBasicBiz extends BusinessBiz<VMeasurementBasicMapper,VMeasurementBasic> {

    @Override
    public void insertSelective(VMeasurementBasic entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public void insert(VMeasurementBasicVo vo){
        if(!StringUtils.isEmpty(vo.getStandardValue()) && vo.getStandardValue().split(",").length>1){
            String[] valueArr = vo.getStandardValue().split(",");
            for (String value:valueArr) {
                VMeasurementBasic entity = new VMeasurementBasic();
                entity.setId(UUIDUtils.generateUuid());
                entity.setCheckType(vo.getCheckType());
                entity.setStandardRange(vo.getStandardRange());
                entity.setStandardValue(Float.valueOf(value));
                entity.setTempletedId(vo.getTempletedId());
                EntityUtils.setCreateInfo(entity);
                super.insertSelective(entity);
            }
        }else{
            VMeasurementBasic entity = new VMeasurementBasic();
            entity.setId(UUIDUtils.generateUuid());
            entity.setCheckType(vo.getCheckType());
            entity.setStandardRange(vo.getStandardRange());
            entity.setStandardValue(Float.valueOf(vo.getStandardValue()==null?"0":vo.getStandardValue()));
            entity.setTempletedId(vo.getTempletedId());
            EntityUtils.setCreateInfo(entity);
            super.insertSelective(entity);
        }
    }


}