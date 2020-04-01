package com.sibo.business.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.EntityUtils;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.enums.FileType;
import com.sibo.business.mapper.VBusinessFileMapper;
import com.sibo.business.vo.VMeteringMethodVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sibo.business.entity.VMeteringMethod;
import com.sibo.business.mapper.VMeteringMethodMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 
 *
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-07 14:39:47
 */
@Service
@Transactional
public class VMeteringMethodBiz extends BusinessBiz<VMeteringMethodMapper,VMeteringMethod> {

    @Autowired
    private VBusinessFileMapper businessFileMapper;

    @Override
    public void insertSelective(VMeteringMethod entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }

    public List<VMeteringMethod> findList() {
        Example example = new Example(VMeteringMethod.class);
        example.setOrderByClause("METHOD_NAME asc");
        List<VMeteringMethod> list = this.selectByExample(example);
        return list;
    }

    public TableResultResponse<VMeteringMethod> queryAll(){
        Example example = new Example(VMeteringMethod.class);
        example.setOrderByClause("METHOD_NAME asc");
        List<VMeteringMethod> list = this.selectByExample(example);
        return new TableResultResponse<VMeteringMethod>(list.size(), list);
    }

    @Override
    public TableResultResponse<VMeteringMethod> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(query.get("methodName"))){
            criteria.andCondition("upper(METHOD_NAME) like upper('%"+query.get("methodName")+"%') or lower(METHOD_NAME) like lower('%"+query.get("methodName")+"%')  or METHOD_NAME like '%"+query.get("methodName")+"%'");
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        example.setOrderByClause("METHOD_NAME ASC");
        List<VMeteringMethod> list = this.selectByExample(example);
        return new TableResultResponse<VMeteringMethod>(result.getTotal(), list);
    }

    public void addMethod(VMeteringMethodVo vo){
        if(!StringUtils.isEmpty(vo.getMethodFileList()) && !CollectionUtils.isEmpty(vo.getTempletedFileList())){

            String method = vo.getMethodFileList();
            String name = method.substring(method.lastIndexOf("/")+1);
            VMeteringMethod model = new VMeteringMethod();
            model.setId(UUIDUtils.generateUuid());
            model.setMethodName(name.substring(0,name.lastIndexOf(".")));
            model.setMethodFilePath(vo.getMethodFilePath());
            EntityUtils.setCreateInfo(model);
            super.insertSelective(model);

            for (String path: vo.getTempletedFileList()) {
                VBusinessFile file  = new VBusinessFile();
                file.setBussinessId(model.getId());
                file.setFileName(path.substring(path.lastIndexOf("/")+1));
                file.setFilePath(path);
                file.setId(UUIDUtils.generateUuid());
                file.setType(FileType.GENERAL.getCode());
                file.setTempletedCode(vo.getTempletedCode());
                businessFileMapper.insertSelective(file);
            }
        }
    }
}