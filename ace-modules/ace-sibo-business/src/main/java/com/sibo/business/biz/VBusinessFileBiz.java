package com.sibo.business.biz;

import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.sibo.business.entity.VBusinessFile;
import com.sibo.business.enums.FileType;
import com.sibo.business.mapper.VBusinessFileMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-15 10:55:56
 */
@Service
public class VBusinessFileBiz extends BusinessBiz<VBusinessFileMapper,VBusinessFile> {

    @Autowired
    private VBusinessFileMapper vBusinessFileMapper;

    public List<VBusinessFile> queryFileList(Map<String, Object> params){
        RowBounds rb = null;
        if(null != params.get("page") && null != params.get("limit")){
            rb = new RowBounds(Integer.parseInt(params.get("page").toString())-1, Integer.parseInt(params.get("limit").toString()));
        }else{
            rb = new RowBounds();
        }

        Map<String, Object> paramMmap = new HashMap<>();
        if(null != params.get("type")){
            paramMmap.put("type",params.get("type"));
        }

        if(null != params.get("order")){
            paramMmap.put("order",params.get("order"));
        }

        if(!org.springframework.util.StringUtils.isEmpty(params.get("year"))){
            paramMmap.put("crtTimeStartTime",params.get("year"));
        }


        List<VBusinessFile>  list =  vBusinessFileMapper.selectFileAll(rb,paramMmap);
        return list;
    }
}