package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.VAssetsReceiveRecords;
import com.sibo.business.vo.VAssetsReceiveVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 资产领用记录
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-22 14:27:58
 */
@Repository
public interface VAssetsReceiveMapper extends CommonMapper<VAssetsReceiveRecords> {

    public List<VAssetsReceiveVo> selectall(RowBounds rb, @Param("assetsReceiveVo")VAssetsReceiveVo assetsReceiveVo);//需要传RowBounds 类型的参数

    /**
     * 记录表和主表联合查询（借用）
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsReceiveVo> selectExample(Map<String, Object> paramMmap);

    /**
     * 记录表和主表联合查询
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsReceiveVo> selectConjunctiveRecord(Map<String, Object> paramMmap);

    /**
     * 统计资产流转记录
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsReceiveVo> selectReceiveRecordAll( Map<String, Object> paramMmap);


    /**
     * 统计资产流转记录
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsReceiveVo> selectReceiveRecordAllLike( Map<String, Object> paramMmap);


    public Integer selectVersion(Map<String, Object> paramMmap);
}
