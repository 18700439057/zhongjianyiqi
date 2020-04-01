package com.sibo.business.calculate.impl;

import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.sibo.business.calculate.AnalysisFactory;
import com.sibo.business.entity.VSourceRecord;
import com.sibo.business.vo.VSourceRecordVo;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;
import java.util.Map;

/**
 * 外部凸出物检具（小）
 */
public class MinExternalProjectionFixtureImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list) throws BusinessRuntimeException {

        return map;
    }

    @Override
    public Map<String, Object> exportExcel(List<VSourceRecord> sourceList, Map<String, Object> datas) throws Exception {
        VSourceRecordVo vo = null;
        for (VSourceRecord vSourceRecord : sourceList) {
            String category = vSourceRecord.getFormula();
            if(category.contains("锥角角度")){
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other",vo);
            }
        }
        datas.put("filePath", "外部凸出物检具（小）.xlsx");
        return datas;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new MinExternalProjectionFixtureImpl();
    }

}
