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
 * B型试验弯指
 */
public class BendTestRefersImpl   implements AnalysisFactory {

    @Override
    public Map importExcel(XSSFSheet xssfSheet, Map map, List<VSourceRecord> list)  throws BusinessRuntimeException {
        List vStandardList = new ArrayList();
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 14; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell value = xssfRow.getCell(4);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
				XSSFCell value1 = xssfRow.getCell(9);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
				if(i==5){
					vStandard.setName(ImportExcelUtils.getValue(value));
					vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
				}else if(i == 6 || i==9 || i== 12){
					vStandard.setModelNumber(ImportExcelUtils.getValue(value));
					vStandard.setValidity(ImportExcelUtils.getValue(value1));
				}else if(i == 7 || i==10 || i== 13){
					vStandard.setCode(ImportExcelUtils.getValue(value));
					vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
					vStandardList.add(vStandard);
				}else if(i == 8 || i == 11){
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

        for (int i = 18; i <35; i++) {
            if(i==27 || i == 28 || i == 29){
                continue;
            }
			try{
					
				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell project = xssfRow.getCell(0);
				XSSFCell standarValue = xssfRow.getCell(4);
				double averageValue = xssfRow.getCell(11).getNumericCellValue();
				double intrinsicError = xssfRow.getCell(12).getNumericCellValue();
				Pattern pattern = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
				if( StringUtils.isNotEmpty(averageValue+"") && StringUtils.isNotEmpty(intrinsicError+"") &&  StringUtils.isNotEmpty(standarValue+"") ){
					Matcher value1IsNum = pattern.matcher(standarValue+"");
					if(!value1IsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}


					String averageValueNew = String.format("%.2f", averageValue);
					Matcher averageValueIsNum = pattern.matcher(averageValueNew+"");
					if(!averageValueIsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}

					String intrinsicErrorErrorNew = String.format("%.2f", intrinsicError);
					Matcher intrinsicErrorIsNum = pattern.matcher(intrinsicErrorErrorNew+"");
					if(!intrinsicErrorIsNum.matches()){
						throw new BusinessRuntimeException("值错误");
					}


					VSourceRecord record = new VSourceRecord();
					record.setId(UUIDUtils.generateUuid());
					record.setFormula(ImportExcelUtils.getValue(project));
					record.setStandardValues(Float.valueOf(ImportExcelUtils.getValue(standarValue)));
					record.setAverageValue(Float.valueOf(averageValueNew));
					record.setIntrinsicError(Float.valueOf(intrinsicErrorErrorNew));
					list.add(record);
				}
			}catch (Exception e){
                throw new BusinessRuntimeException("第" + i + "行获取数据异常，请检查模板是否正确");
            }
        }
        //温度湿度
        VMeasurementCertificate vMeasurementCertificate = new VMeasurementCertificate();
        for (int i = 36; i <38; i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell wd = xssfRow.getCell(8);
				if(i==36){
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
        for (int i = 38; i <= xssfSheet.getLastRowNum(); i++) {
			try{

				XSSFRow xssfRow = xssfSheet.getRow(i);
				XSSFCell year = xssfRow.getCell(6);
				XSSFCell month = xssfRow.getCell(7);
				XSSFCell day = xssfRow.getCell(8);
				if(i==38){
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
    public Map<String, Object> exportExcel(List<VSourceRecord> sourceList,Map<String, Object> datas) throws Exception {
        //处理测量值并计量修正值
        List  list = new ArrayList<>();
        VSourceRecordVo vo = null;
        for (VSourceRecord vSourceRecord : sourceList) {
            if(null == vSourceRecord.getFormula() || vSourceRecord.getFormula().substring(0,2).equals("推力")){
                vo = new VSourceRecordVo();
                vo.setStandardValues(vSourceRecord.getStandardValues());
                vo.setAverageValue(vSourceRecord.getAverageValue());
                vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                list.add(vo);
            }else{
                String name = vSourceRecord.getFormula().substring(0,4);
                if(name.equals("指尖至挡")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other1",vo);
                }else if(name.equals("指尖至铰")){
                    name = vSourceRecord.getFormula().substring(0,7);
                    if(name.equals("指尖至铰链第一")){
                        vo = new VSourceRecordVo();
                        vo.setStandardValues(vSourceRecord.getStandardValues());
                        vo.setAverageValue(vSourceRecord.getAverageValue());
                        vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                        datas.put("other2",vo);
                    }else{
                        vo = new VSourceRecordVo();
                        vo.setStandardValues(vSourceRecord.getStandardValues());
                        vo.setAverageValue(vSourceRecord.getAverageValue());
                        vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                        datas.put("other9",vo);
                    }

                }else if(name.equals("指尖至护")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other3",vo);
                }else if(name.equals("指部直径")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other4",vo);
                }else if(name.equals("挡盘直径")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other5",vo);
                }else if(name.equals("挡盘宽度")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other6",vo);
                }
                else if(name.equals("护板直径")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other7",vo);
                }
                else if(name.equals("护板厚度")){
                    vo = new VSourceRecordVo();
                    vo.setStandardValues(vSourceRecord.getStandardValues());
                    vo.setAverageValue(vSourceRecord.getAverageValue());
                    vo.setIntrinsicError(vSourceRecord.getIntrinsicError());
                    datas.put("other8",vo);
                }
            }


        }
        datas.put("sourceList",list);
        datas.put("filePath","B型试验弯指.xlsx");
        return datas;
    }

    @Override
    public AnalysisFactory getAnalysis() {
        return new BendTestRefersImpl();
    }
}
