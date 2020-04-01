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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 非接触式车载路试仪
 */
public class RoadTestInstrumentImpl implements AnalysisFactory {
    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessException {
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 8; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(4);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(12);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if(i==5){
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}else if(i == 6){
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				}else{
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        map.put("vStandard",vStandard);

        for (int i = 12; i <= xssfSheet.getLastRowNum()-6; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell standarValue = xssfRow.getCell(3);
				double averageValue = xssfRow.getCell(14).getNumericCellValue();
				double intrinsicError = xssfRow.getCell(17).getNumericCellValue();
				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(averageValue+"")  &&  StringUtils.isNotEmpty(standarValue+"")  && StringUtils.isNotEmpty(intrinsicError+"") ){

					Matcher averageValueIsNum = pattern.matcher(averageValue+"");
					if(!averageValueIsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;平均值数据值错误");
					}




					Matcher value6IsNum = pattern.matcher(intrinsicError+"");
					if(!value6IsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;基本误差值错误");
					}


					Matcher standarValueIsNum = pattern.matcher(standarValue+"");
					if(!standarValueIsNum.matches()){
						throw new BusinessRuntimeException("第"+i+"行;标准值错误");
					}


					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setStandardValues(Float.valueOf(ImportExcelUtils.getValue(standarValue)));
					record.setAverageValue(Float.valueOf(averageValue+""));
					record.setIntrinsicError(Float.valueOf(intrinsicError+""));
					list.add(record);
				}
            }catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 27; i <29; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(10);
				if(i==27){
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
        for (int i = 29; i <= xssfSheet.getLastRowNum(); i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(7);
				XSSFCell month = xssfRow.getCell(10);
				XSSFCell day = xssfRow.getCell(12);
				if(i==29){
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
    public Map exportExcel(List<VSourceRecord> sourceList,Map<String, Object> resultMap) throws BusinessException,Exception {
        //处理测量值并计量修正值
        List<VSourceRecordVo>  list = new ArrayList<>();
        float[] valueArr = new float[4];
        Map<Float,Float> map = new HashMap<>();
        for (VSourceRecord vSourceRecord : sourceList) {
            VSourceRecordVo vo = new VSourceRecordVo();
            org.springframework.beans.BeanUtils.copyProperties(vSourceRecord,vo);
            list.add(vo);
        }
        resultMap.put("sourceList",list);
//        String tmpFile = "classpath:static/摩托车工况路试仪.xls";
//        File file = ResourceUtils.getFile(tmpFile);
        resultMap.put("filePath","摩托车工况路试仪.xls");
        return resultMap;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new RoadTestInstrumentImpl();
    }
}
