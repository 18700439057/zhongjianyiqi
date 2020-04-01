package com.sibo.business.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 原始记录表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-10 18:33:32
 */
public class VSourceRecordVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    private String id;

	    //标准值
    private Float standardValues;

	    //测量值2
    private Float value2;

	    //测量值3
    private Float value3;

	    //测量值4
    private Float value4;

	    //测量值5
    private Float value5;

	    //测量值6
    private Float value6;
	
	    //测量值7
    private Float value7;
	
	    //测量值8
    private Float value8;
	
	    //测量值9
    private Float value9;
	
	    //测量值10
    private Float value10;
	
	    //测量值1
    private Float value1;

	//基本误差
	private Float intrinsicError;

	//平均值
	private Float averageValue;

	//示值误差
	private Float  indicationError;
	
	    //类型
    private Float type;
	
	    //创建人名称
    private String crtUserName;
	
	    //创建人id
    private String crtUserId;
	
	    //创建时间
    private Date crtTime;
	
	    //更新人名称
    private String updUserName;
	
	    //更新人id
    private String updUserId;
	
	    //更新时间
    private Date updTime;

    //证书id
	private String measurementCertificateId;

	//测量值
	private Float testValue;

	//误差
	private Float errorValue;

	//设定值
	private String setValue;

	//变动性
	private String variability;

	//准确度
	private String accuracy;

	private String formula;

	private String remark;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：标准值
	 */
	public void setStandardValues(Float standardValues) {
		this.standardValues = standardValues;
	}
	/**
	 * 获取：标准值
	 */
	public Float getStandardValues() {
		return standardValues;
	}
	/**
	 * 设置：测量值2
	 */
	public void setValue2(Float value2) {
		this.value2 = value2;
	}
	/**
	 * 获取：测量值2
	 */
	public Float getValue2() {
		return value2;
	}
	/**
	 * 设置：测量值3
	 */
	public void setValue3(Float value3) {
		this.value3 = value3;
	}
	/**
	 * 获取：测量值3
	 */
	public Float getValue3() {
		return value3;
	}
	/**
	 * 设置：测量值4
	 */
	public void setValue4(Float value4) {
		this.value4 = value4;
	}
	/**
	 * 获取：测量值4
	 */
	public Float getValue4() {
		return value4;
	}
	/**
	 * 设置：测量值5
	 */
	public void setValue5(Float value5) {
		this.value5 = value5;
	}
	/**
	 * 获取：测量值5
	 */
	public Float getValue5() {
		return value5;
	}
	/**
	 * 设置：测量值6
	 */
	public void setValue6(Float value6) {
		this.value6 = value6;
	}
	/**
	 * 获取：测量值6
	 */
	public Float getValue6() {
		return value6;
	}
	/**
	 * 设置：测量值7
	 */
	public void setValue7(Float value7) {
		this.value7 = value7;
	}
	/**
	 * 获取：测量值7
	 */
	public Float getValue7() {
		return value7;
	}
	/**
	 * 设置：测量值8
	 */
	public void setValue8(Float value8) {
		this.value8 = value8;
	}
	/**
	 * 获取：测量值8
	 */
	public Float getValue8() {
		return value8;
	}
	/**
	 * 设置：测量值9
	 */
	public void setValue9(Float value9) {
		this.value9 = value9;
	}
	/**
	 * 获取：测量值9
	 */
	public Float getValue9() {
		return value9;
	}
	/**
	 * 设置：测量值10
	 */
	public void setValue10(Float value10) {
		this.value10 = value10;
	}
	/**
	 * 获取：测量值10
	 */
	public Float getValue10() {
		return value10;
	}
	/**
	 * 设置：测量值1
	 */
	public void setValue1(Float value1) {
		this.value1 = value1;
	}
	/**
	 * 获取：测量值1
	 */
	public Float getValue1() {
		return value1;
	}
	/**
	 * 设置：类型
	 */
	public void setType(Float type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public Float getType() {
		return type;
	}
	/**
	 * 设置：创建人名称
	 */
	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}
	/**
	 * 获取：创建人名称
	 */
	public String getCrtUserName() {
		return crtUserName;
	}
	/**
	 * 设置：创建人id
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：创建人id
	 */
	public String getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCrtTime() {
		return crtTime;
	}
	/**
	 * 设置：更新人名称
	 */
	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}
	/**
	 * 获取：更新人名称
	 */
	public String getUpdUserName() {
		return updUserName;
	}
	/**
	 * 设置：更新人id
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：更新人id
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdTime() {
		return updTime;
	}

	public String getMeasurementCertificateId() {
		return measurementCertificateId;
	}

	public void setMeasurementCertificateId(String measurementCertificateId) {
		this.measurementCertificateId = measurementCertificateId;
	}

	public Float getTestValue() {
		return testValue;
	}

	public void setTestValue(Float testValue) {
		this.testValue = testValue;
	}

	public Float getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(Float errorValue) {
		this.errorValue = errorValue;
	}

	public Float getIntrinsicError() {
		return intrinsicError;
	}

	public void setIntrinsicError(Float intrinsicError) {
		this.intrinsicError = intrinsicError;
	}

	public Float getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(Float averageValue) {
		this.averageValue = averageValue;
	}

	public Float getIndicationError() {
		return indicationError;
	}

	public void setIndicationError(Float indicationError) {
		this.indicationError = indicationError;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}

	public String getVariability() {
		return variability;
	}

	public void setVariability(String variability) {
		this.variability = variability;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
