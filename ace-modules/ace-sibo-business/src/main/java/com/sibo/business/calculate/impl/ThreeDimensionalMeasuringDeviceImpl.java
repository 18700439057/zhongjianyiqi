package com.sibo.business.calculate.impl;

import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.calculate.AnalysisFactory;
import com.sibo.business.entity.VMeasurementCertificate;
import com.sibo.business.entity.VSourceRecord;
import com.sibo.business.entity.VStandard;
import com.sibo.business.utils.ImportExcelUtils;
import com.sibo.business.vo.VSourceRecordVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 三维H点测量装置
 */
public class ThreeDimensionalMeasuringDeviceImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list) throws BusinessRuntimeException {

        return map;
    }

    @Override
    public Map<String, Object> exportExcel(List<VSourceRecord> sourceList, Map<String, Object> datas) throws Exception {
        VSourceRecordVo vo = null;
        for (VSourceRecord vSourceRecord : sourceList) {
            String name = vSourceRecord.getFormula();
            if (name.contains("眼瞳距离")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other",vo);
            } else if (name.contains("H点高度")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other1",vo);
            } else if (name.contains("装置质量")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                datas.put("other2",vo);
            }

        }
        datas.put("filePath", "三维H点测量装置.xlsx");
        return datas;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new ThreeDimensionalMeasuringDeviceImpl();
    }
}
