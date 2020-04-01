package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计量/核查计划表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Table(name = "v_measurement_verification")
public class VMeasurementVerificationPlan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSET_ID")
    private String assetId;
	
	    //计量/核查状态
    @Column(name = "MEASUEEMENT_VERIFICATION_STATU")
    private String measueementVerificationStatu;

	//计量有效期
	@Column(name = "MEASUREMENT_VALIDITY")
	private Date measurementValidity;
	
	    //计量/核查类型（1：计量2.核查）
    @Column(name = "TYPE")
    private String type;
	
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
	 * 设置：资产ID
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * 获取：资产ID
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * 设置：计量/核查状态
	 */
	public void setMeasueementVerificationStatu(String measueementVerificationStatu) {
		this.measueementVerificationStatu = measueementVerificationStatu;
	}
	/**
	 * 获取：计量/核查状态
	 */
	public String getMeasueementVerificationStatu() {
		return measueementVerificationStatu;
	}
	/**
	 * 设置：计量/核查类型（1：计量2.核查）
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：计量/核查类型（1：计量2.核查）
	 */
	public String getType() {
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

	public Date getMeasurementValidity() {
		return measurementValidity;
	}

	public void setMeasurementValidity(Date measurementValidity) {
		this.measurementValidity = measurementValidity;
	}
}
