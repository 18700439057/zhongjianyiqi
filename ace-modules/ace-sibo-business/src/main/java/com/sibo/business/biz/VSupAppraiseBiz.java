package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.entity.VSupAppraise;
import com.sibo.business.entity.VSupSupplier;
import com.sibo.business.enums.FileType;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.mapper.VSupAppraiseMapper;
import com.sibo.business.vo.VBusinessFileVo;
import com.sibo.business.vo.VSupAppraiseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 
 *
 * @author liulong
 * @version 2018-10-10 18:02:22
 */
@Service
public class VSupAppraiseBiz extends BusinessBiz<VSupAppraiseMapper,VSupAppraise> {

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    public String insertSelective(VSupAppraiseVo vo) {
        VSupAppraise model = new VSupAppraise();
        BeanUtils.copyProperties(vo,model);
        model.setCommentTime(new Date());
        model.setId(UUIDUtils.generateUuid());
        super.insertSelective(model);

        if(null != vo.getImageList() && vo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vo.getImageList()) {
                VBusinessFile file  = new VBusinessFile();
                file.setBussinessId(model.getId());
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")+1));
                //file.setCreateDate(new Date());
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }
        return model.getId();
    }

    public VSupAppraiseVo updateById(VSupAppraiseVo vo) {
        VSupAppraise entity = new VSupAppraise();
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