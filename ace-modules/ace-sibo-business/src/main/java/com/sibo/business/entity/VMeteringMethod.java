package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-12-07 14:39:47
 */
@Table(name = "v_metering_method")
public class VMeteringMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产id
    @Column(name = "ASSET_ID")
    private String assetId;
	
	    //方法名称
    @Column(name = "METHOD_NAME")
    private String methodName;
	
	    //方法编码
    @Column(name = "METHOD_CODE")
    private String methodCode;
	
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

    //方法附件
	@Column(name = "METHOD_FILE_PATH")
    private String methodFilePath;
	

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
	 * 设置：资产id
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * 获取：资产id
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * 设置：方法名称
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：方法名称
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：方法编码
	 */
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	/**
	 * 获取：方法编码
	 */
	public String getMethodCode() {
		return methodCode;
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

	public String getMethodFilePath() {
		return methodFilePath;
	}

	public void setMethodFilePath(String methodFilePath) {
		this.methodFilePath = methodFilePath;
	}
}
