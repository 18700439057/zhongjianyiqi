package com.sibo.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 原始记录表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-10 18:33:32
 */
@Table(name = "v_source_record")
public class VSourceRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;

	    //标准值
    @Column(name = "STANDARD_VALUES")
    private Float standardValues;

	    //测量值2
    @Column(name = "VALUE2")
    private Float value2;

	    //测量值3
    @Column(name = "VALUE3")
    private Float value3;

	    //测量值4
    @Column(name = "VALUE4")
    private Float value4;

	    //测量值5
    @Column(name = "VALUE5")
    private Float value5;

	    //测量值6
    @Column(name = "VALUE6")
    private Float value6;
	
	    //测量值7
    @Column(name = "VALUE7")
    private Float value7;
	
	    //测量值8
    @Column(name = "VALUE8")
    private Float value8;
	
	    //测量值9
    @Column(name = "VALUE9")
    private Float value9;
	
	    //测量值10
    @Column(name = "VALUE10")
    private Float value10;
	
	    //测量值1
    @Column(name = "VALUE1")
    private Float value1;

	//基本误差
	@Column(name = "INTRINSIC_ERROR")
	private Float intrinsicError;

	//平均值
	@Column(name = "AVERAGE_VALUE")
	private Float averageValue;

	//示值误差
	@Column(name = "INDICATION_ERROR")
	private Float  indicationError;
	
	    //类型
    @Column(name = "TYPE")
    private Float type;
	
	    //创建人名称
    @Column(name = "CRT_USER_NAME")
    private String crtUserName;
	
	    //创建人id
    @Column(name = "CRT_USER_ID")
    private String crtUserId;
	
	    //创建时间
    @Column(name = "CRT_TIME")
    private Date crtTime;
	
	    //更新人名称
    @Column(name = "UPD_USER_NAME")
    private String updUserName;
	
	    //更新人id
    @Column(name = "UPD_USER_ID")
    private String updUserId;
	
	    //更新时间
    @Column(name = "UPD_TIME")
    private Date updTime;

    //证书id
	@Column(name = "MEASUREMENT_CERTIFICATE_ID")
	private String measurementCertificateId;

	//设定值
	@Column(name = "SET_VALUE")
	private String setValue;

	//公式
	@Column(name = "FORMULA")
	private String formula;

	//备注
	@Column(name = "REMARK")
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
