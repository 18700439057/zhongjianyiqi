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
 * 家电电源线弯曲试验机
 */
public class AppliancePowerCordBendingTesterImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list) throws BusinessRuntimeException {
        List vStandardList = new ArrayList();
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 11; i++) {
            try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(5);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if (i == 5) {
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				} else if (i == 6 || i == 9 ) {
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				} else if (i == 7 || i == 10) {
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
					vStandardList.add(vStandard);
				} else if (i == 8 ) {
					vStandard = new VStandard();
					vStandard.setId(UUIDUtils.generateUuid());
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }

        }
        map.put("vStandardList", vStandardList);

        String bigCatefory = "";
        for (int i = 15; i < 31; i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i);
            XSSFCell projectBigCatefory = xssfRow.getCell(0);
            if (StringUtils.isNotEmpty(ImportExcelUtils.getValue(projectBigCatefory))) {
                bigCatefory = ImportExcelUtils.getValue(projectBigCatefory);
            }
            XSSFCell standardValues = xssfRow.getCell(2);
            try {
                double averageValue = xssfRow.getCell(6).getNumericCellValue();
                double intrinsicError = xssfRow.getCell(8).getNumericCellValue();
                Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                if (StringUtils.isNotEmpty(standardValues + "") && StringUtils.isNotEmpty(intrinsicError + "") && StringUtils.isNotEmpty(averageValue + "")) {
                    Matcher value1IsNum = pattern.matcher(standardValues + "");
                    if (!value1IsNum.matches()) {
                        throw new BusinessRuntimeException("值错误");
                    }


                    String averageValueNew = String.format("%.2f", averageValue);
                    Matcher averageValueIsNum = pattern.matcher(averageValueNew + "");
                    if (!averageValueIsNum.matches()) {
                        throw new BusinessRuntimeException("值错误");
                    }

                    String intrinsicErrorErrorNew = String.format("%.2f", intrinsicError);
                    Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorErrorNew + "");
                    if (!intrinsicErrorIsNum.matches()) {
                        throw new BusinessRuntimeException("值错误");
                    }


                    VSourceRecord record = new VSourceRecord();
                    record.setId(UUIDUtils.generateUuid());
                    record.setFormula(bigCatefory);
                    record.setStandardValues(Float.valueOf(ImportExcelUtils.getValue(standardValues)));
                    record.setAverageValue(Float.valueOf(averageValueNew));
                    record.setIntrinsicError(Float.valueOf(intrinsicErrorErrorNew));
                    list.add(record);
                }
            } catch (Exception e) {
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }

        }
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 32; i < 34; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(5);
				if (i == 32) {
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));
				} else {
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vMeasurementCertificate", vMeasurementCertificate);

        //计量日期
        for (int i = 34; i <= xssfSheet.getLastRowNum(); i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(4);
				XSSFCell month = xssfRow.getCell(5);
				XSSFCell day = xssfRow.getCell(6);
				if (i == 34) {
					map.put("recentMeasurementPlanTime", ImportExcelUtils.getValue(year) + ImportExcelUtils.getValue(month) + ImportExcelUtils.getValue(day));
				} else {
					map.put("measurementValidity", ImportExcelUtils.getValue(year) + ImportExcelUtils.getValue(month) + ImportExcelUtils.getValue(day));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("list", list);
        return map;
    }

    @Override
    public Map<String, Object> exportExcel(List<VSourceRecord> sourceList, Map<String, Object> datas) throws Exception {
        List list = new ArrayList<>();
        List list1 = new ArrayList<>();
        List list2 = new ArrayList<>();
        List list3 = new ArrayList<>();
        VSourceRecordVo vo = null;
        for (VSourceRecord vSourceRecord : sourceList) {
            String name = vSourceRecord.getFormula();
            if (name.contains("电压")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                list.add(vo);
            } else if (name.contains("电流")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                list1.add(vo);
            } else if (name.contains("顺时针")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                list2.add(vo);
            } else if (name.contains("逆时针")) {
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                list3.add(vo);
            }

        }
        datas.put("sourceList", list);
        datas.put("sourceList1", list1);
        datas.put("sourceList2", list2);
        datas.put("sourceList3", list3);
        datas.put("filePath", "家电电源线弯曲扭转试验机.xlsx");
        return datas;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new AppliancePowerCordBendingTesterImpl();
    }
}
