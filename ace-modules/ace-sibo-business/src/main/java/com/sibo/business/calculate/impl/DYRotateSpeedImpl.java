package com.sibo.business.calculate.impl;

import com.github.wxiaoqi.security.common.exception.base.BusinessException;
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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DY转速
 */
public class DYRotateSpeedImpl implements AnalysisFactory {
    @Override
    public AnalysisFactory getAnalysis() {
        return new DYRotateSpeedImpl();
    }

    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list) throws BusinessException {
        //处理计量标准器
//        VStandard vStandard = new VStandard();
//        vStandard.setId(UUIDUtils.generateUuid());
//        for (int i = 4; i < 6; i++) {
//            XSSFRow xssfRow = xssfSheet.getRow(i);
//            XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为准确度
//            XSSFCell value1 = xssfRow.getCell(6);//第一次循环取到为型号  第二次循环取到编号
//            XSSFCell value2 = xssfRow.getCell(10);//第一次循环取到为证书编号  第二次循环取到有效期
//            if(i==4){
//                vStandard.setName(ImportExcelUtils.getValue(value));
//                vStandard.setModelNumber(ImportExcelUtils.getValue(value1));
//                vStandard.setCertificateNumber(ImportExcelUtils.getValue(value2));
//            }else{
//                vStandard.setAccuracy(ImportExcelUtils.getValue(value));
//                vStandard.setCode(ImportExcelUtils.getValue(value1));
//                vStandard.setValidity(ImportExcelUtils.getValue(value2));
//            }
//        }
        VStandard vStandard = ImportExcelUtils.getStandard3(xssfSheet);
        map.put("vStandard",vStandard);

        //处理具体值
        for (int i = 10; i <= xssfSheet.getLastRowNum()-5; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell setValue = xssfRow.getCell(2);//设定值2
				double standarValue = xssfRow.getCell(3).getNumericCellValue();//标准值

				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if(StringUtils.isNotEmpty(standarValue+"") &&  StringUtils.isNotEmpty(setValue+"")){

					Matcher value1IsNum = pattern.matcher(setValue+"");
					if(!value1IsNum.matches()){
						throw new BusinessException("值错误");
					}

					BigDecimal standarValueBg = new BigDecimal(standarValue).setScale(2, RoundingMode.UP);
					float standarValueNew = standarValueBg.floatValue();
					Matcher standarValueIsNum = pattern.matcher(standarValueNew+"");
					if(!standarValueIsNum.matches()){
						throw new BusinessException("值错误");
					}



					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setStandardValues(standarValueNew);//标准值
					record.setSetValue(ImportExcelUtils.getValue(setValue));

					//计算示值误差
					float indicationError =  Float.valueOf(record.getSetValue())-record.getStandardValues();
					BigDecimal indicationErrorBg = new BigDecimal(indicationError).setScale(2, RoundingMode.UP);
					float indicationErrorNew = indicationErrorBg.floatValue();
					record.setIndicationError(indicationErrorNew);//示值误差
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }

        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i =17; i <19; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(10);
				if(i==17){
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));
				}else{
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vMeasurementCertificate",vMeasurementCertificate);

        for (int i = 19; i <21; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(7);
				XSSFCell month = xssfRow.getCell(8);
				XSSFCell day = xssfRow.getCell(9);
				if(i==19){
					map.put("recentMeasurementPlanTime",ImportExcelUtils.getValue(year)+ImportExcelUtils.getValue(month)+ImportExcelUtils.getValue(day));
				}else{
					map.put("measurementValidity",ImportExcelUtils.getValue(year)+ImportExcelUtils.getValue(month)+ImportExcelUtils.getValue(day));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }

        }
        map.put("list",list);
        return map;
    }

    @Override
    public Map exportExcel(List<VSourceRecord> sourceList, Map<String, Object> resultMap) throws BusinessException, Exception {
        List<VSourceRecordVo>  list = new ArrayList<>();
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            vo.setStandardValues(vSourceRecord.getStandardValues());
            vo.setSetValue(vSourceRecord.getSetValue());
            vo.setIndicationError(vSourceRecord.getIndicationError());
            list.add(vo);
        }
//        String tmpFile = "classpath:static/DY转速.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","DY转速.xls");
        resultMap.put("sourceList",list);
        return resultMap;
    }
}
