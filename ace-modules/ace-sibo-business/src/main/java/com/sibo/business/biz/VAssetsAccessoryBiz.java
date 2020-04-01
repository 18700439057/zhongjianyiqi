package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VAssetsAccessory;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.enums.FileType;
import com.sibo.business.mapper.VAssetsAccessoryMapper;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.vo.VAssetsAccessoryVo;
import com.sibo.business.vo.VAssetsParameterVo;
import com.sibo.business.vo.VBusinessFileVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * 设备资料
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:35
 */
@Service
@Transactional
public class VAssetsAccessoryBiz extends BusinessBiz<VAssetsAccessoryMapper,VAssetsAccessory> {

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    @Autowired
    private VAssetsAccessoryMapper assetsAccessoryMapper;

    public String insertSelective(VAssetsAccessoryVo vo) {
        VAssetsAccessory entity = new VAssetsAccessory();
        BeanUtils.copyProperties(vo,entity);
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);

        //保存附件信息
        if(null != vo.getImageList() && vo.getImageList().size()>0){
            for (VBusinessFileVo fileVo:vo.getImageList()) {
                VBusinessFile file  = new VBusinessFile();
                file.setBussinessId(entity.getId());
                file.setFileName(fileVo.getFilePath().substring(fileVo.getFilePath().lastIndexOf("/")));
                file.setFilePath(fileVo.getFilePath());
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                businessFileMapper.insertSelective(file);
            }
        }
        return entity.getId();
    }

    public VAssetsAccessoryVo updateAdditionalAsset(VAssetsAccessoryVo vo) {
        VAssetsAccessory entity = new VAssetsAccessory();
        BeanUtils.copyProperties(vo,entity);
        super.updateSelectiveById(entity);

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