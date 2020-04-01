package com.sibo.business.calculate;

import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.sibo.business.entity.VSourceRecord;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;
import java.util.Map;

/**
 * 解析原始记录
 */
public interface AnalysisFactory {

    Map importExcel(XSSFSheet xssfSheet,Map map ,List<VSourceRecord> list) throws BusinessRuntimeException;

    Map<String, Object> exportExcel(List<VSourceRecord> sourceList,Map<String, Object> datas)  throws Exception;

    AnalysisFactory getAnalysis();
}
