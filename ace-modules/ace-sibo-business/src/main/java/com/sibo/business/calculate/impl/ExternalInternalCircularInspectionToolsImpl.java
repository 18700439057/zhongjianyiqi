package com.sibo.business.calculate.impl;

import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.sibo.business.calculate.AnalysisFactory;
import com.sibo.business.entity.VSourceRecord;

import com.sibo.business.vo.VSourceRecordVo;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;
import java.util.Map;


/**
 *外方内圆检具
 */
public class ExternalInternalCircularInspectionToolsImpl  implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list) throws BusinessRuntimeException {
        return map;
    }

    @Override
    public Map<String, Object> exportExcel(List<VSourceRecord> sourceList, Map<String, Object> datas) throws Exception {
        VSourceRecordVo vo = null;
        for (VSourceRecord vSourceRecord : sourceList) {
            String category = vSourceRecord.getFormula();
            if(category.contains("直径")){
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other",vo);
            }else if(category.contains("长")){
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other1",vo);
            }else if(category.contains("宽")){
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other2",vo);
            }
        }
        datas.put("filePath", "外方内圆检具.xlsx");
        return datas;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new ExternalInternalCircularInspectionToolsImpl();
    }
}
