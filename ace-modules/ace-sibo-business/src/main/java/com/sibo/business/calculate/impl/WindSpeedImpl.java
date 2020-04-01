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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 风速
 */
public class WindSpeedImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
        List vStandardList = new ArrayList();
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 11; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(4);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(10);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if(i==5){
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}else if(i == 6 || i==9){
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				}else if(i == 7 || i==10){
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
					vStandardList.add(vStandard);
				}else if(i == 8){
					vStandard = new VStandard();
					vStandard.setId(UUIDUtils.generateUuid());
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
            


        }
        map.put("vStandardList",vStandardList);
        Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
        VSourceRecord record = new VSourceRecord();
        for (int i =23; i <26; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				if(i==23){
					XSSFCell value1 = xssfRow.getCell(2);
					if( StringUtils.isNotEmpty(value1+"")){
						Matcher setValueIsNum = pattern.matcher(value1+"");
						if(!setValueIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行设定值错误");
						}
						record.setId(UUIDUtils.generateUuid());
						record.setValue1(Float.valueOf(ImportExcelUtils.getValue(value1)));
					}

				}else if(i == 24){
					XSSFCell value2 = xssfRow.getCell(5);
					if( StringUtils.isNotEmpty(value2+"")){
						Matcher setValueIsNum = pattern.matcher(value2+"");
						if(!setValueIsNum.matches()){
							throw new BusinessRuntimeException("第"+i+"行设定值错误");
						}
						record.setValue2(Float.valueOf(ImportExcelUtils.getValue(value2)));
					}

				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
            
        }
        list.add(record);
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 31; i <33; i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(10);
				if(i==31){
					vMeasurementCertificate.setTemperature(ImportExcelUtils.getValue(wd));
				}else{
					vMeasurementCertificate.setHumidity(ImportExcelUtils.getValue(wd));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
            
        }
        map.put("vMeasurementCertificate",vMeasurementCertificate);

        //计量日期
        for (int i = 33; i <= xssfSheet.getLastRowNum(); i++) {
			try{
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(7);
				XSSFCell month = xssfRow.getCell(8);
				XSSFCell day = xssfRow.getCell(9);
				if(i==33){
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
    public Map exportExcel(List<VSourceRecord> sourceList,Map<String,Object> resultMap) throws Exception {
        //处理测量值并计量修正值
        List<VSourceRecordVo>  list = new ArrayList<>();
        Map<Float,Float> map = new HashMap<>();
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            vo.setValue1(vSourceRecord.getValue1());
            vo.setValue2(vSourceRecord.getValue2());
            list.add(vo);
        }
        resultMap.put("sourceList",list);
//        String tmpFile = "classpath:static/风速.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","风速.xls");
        return resultMap;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new WindSpeedImpl();
    }
}
