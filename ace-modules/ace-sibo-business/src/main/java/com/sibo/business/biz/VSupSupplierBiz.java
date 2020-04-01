package com.sibo.business.biz;

import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.entity.VSupAppraise;
import com.sibo.business.entity.VSupSupplier;
import com.sibo.business.enums.FileType;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.mapper.VSupSupplierMapper;
import com.sibo.business.vo.VBusinessFileVo;
import com.sibo.business.vo.VSupAppraiseVo;
import com.sibo.business.vo.VSupSupplierVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 
 *
 * @author liulong
 * @version 2018-10-10 09:39:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class VSupSupplierBiz extends BusinessBiz<VSupSupplierMapper, VSupSupplier> {

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    @Autowired
    private MergeCore mergeCore;

    @Override
    public VSupSupplier selectById(Object id) {
        VSupSupplier user = super.selectById(id);
        try {
            mergeCore.mergeOne(VSupSupplier.class, user);
            return user;
        } catch (Exception e) {
            return super.selectById(id);
        }
    }

    public String insertSelective(VSupSupplierVo vo) {
        VSupSupplier model = new VSupSupplier();
        BeanUtils.copyProperties(vo,model);
        model.setId(UUIDUtils.generateUuid());
        super.insertSelective(model);

        if(null != vo.getImageList() && vo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vo.getImageList()) {
                VBusinessFile file  = new VBusinessFile();
                file.setBussinessId(model.getId());
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")));
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }

        return model.getId();
    }


    public VSupSupplierVo updateById(VSupSupplierVo vo) {
        VSupSupplier entity = new VSupSupplier();
        BeanUtils.copyProperties(vo,entity);
       //EntityUtils.setUpdatedInfo(entity);
        super.updateById(entity);

        VBusinessFile file  = new VBusinessFile();
        Example example = new Example(VBusinessFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bussinessId", entity.getId());
        int i = businessFileMapper.deleteByExample(example);
        if(null != vo.getImageList() && vo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vo.getImageList()) {
                file  = new VBusinessFile();
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")+1));
                file.setBussinessId(entity.getId());
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }
        return vo;
    }
}