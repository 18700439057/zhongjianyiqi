package com.sibo.business.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.sibo.business.entity.VAssetsParameter;
import com.sibo.business.vo.StatisticsAssetsNumVo;
import com.sibo.business.vo.VAssetsReceiveVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author liulong
 * @version 2018-10-16 14:26:34
 */
@Repository
public interface VAssetsParameterMapper extends CommonMapper<VAssetsParameter> {

    String getMaxNum(Map<String, Object> paramMmap);

    String getOtherMaxNum(Map<String, Object> paramMmap);

    public List<VAssetsParameter> selectAssetsParameterAll(RowBounds rb, Map<String, Object> paramMmap);

    public List<VAssetsParameter> selectAssetsParameterAll(Map<String, Object> paramMmap);

    public List<VAssetsParameter> selectAssetsParameterPageList( Map<String, Object> paramMmap);

    public List<VAssetsParameter> selectEquipmentPageList( Map<String, Object> paramMmap);

    public List<VAssetsParameter> queryLoanPage(RowBounds rb, Map<String, Object> paramMmap);

    /**
     * 计量计划查询
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsParameter> selectAssetsParamerByPlay( Map<String, Object> paramMmap);

    /**
     * 计量计划全局搜索
     * @param paramMmap
     * @return
     */
    public List<VAssetsParameter> selectAssetsParamerByPlayAllLike( Map<String, Object> paramMmap);

    /**
     * 变更查询
     * @param rb
     * @param paramMmap
     * @return
     */
    public List<VAssetsParameter> selectChangePageList(Map<String, Object> paramMmap);

    /**
     * 查询首页统计数据
     * @param year
     * @return
     */
    public List<StatisticsAssetsNumVo> queryDashboardAssetsCount(String _parameter);

    /**
     * 根据资产类别统计查询
     * @param _parameter
     * @return
     */
    public List<StatisticsAssetsNumVo> queryDashboardAssetsCategoryCount(String _parameter);

    /**
     * 计量费用
     * @param _parameter
     * @return
     */
    public List<VAssetsParameter> queryYearMeasurementCost(String _parameter);




	
}
