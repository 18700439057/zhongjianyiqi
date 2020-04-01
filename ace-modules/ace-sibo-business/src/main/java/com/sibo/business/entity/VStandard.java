package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计量标准器
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-13 16:27:47
 */
@Table(name = "v_standard")
public class VStandard implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //名称
    @Column(name = "NAME")
    private String name;
	
	    //编号
    @Column(name = "CODE")
    private String code;
	
	    //型号
    @Column(name = "MODEL_NUMBER")
    private String modelNumber;
	
	    //证书编号
    @Column(name = "CERTIFICATE_NUMBER")
    private String certificateNumber;
	
	    //准确度
    @Column(name = "ACCURACY")
    private String accuracy;
	
	    //有效期
    @Column(name = "VALIDITY")
    private String validity;
	
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
	
	    //
    @Column(name = "MEASUREMENT_CERTIFICATE_ID")
    private String measurementCertificateId;
	

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
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：编号
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：型号
	 */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	/**
	 * 获取：型号
	 */
	public String getModelNumber() {
		return modelNumber;
	}
	/**
	 * 设置：证书编号
	 */
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	/**
	 * 获取：证书编号
	 */
	public String getCertificateNumber() {
		return certificateNumber;
	}
	/**
	 * 设置：准确度
	 */
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	/**
	 * 获取：准确度
	 */
	public String getAccuracy() {
		return accuracy;
	}
	/**
	 * 设置：有效期
	 */
	public void setValidity(String validity) {
		this.validity = validity;
	}
	/**
	 * 获取：有效期
	 */
	public String getValidity() {
		return validity;
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
	/**
	 * 设置：
	 */
	public void setMeasurementCertificateId(String measurementCertificateId) {
		this.measurementCertificateId = measurementCertificateId;
	}
	/**
	 * 获取：
	 */
	public String getMeasurementCertificateId() {
		return measurementCertificateId;
	}
}
