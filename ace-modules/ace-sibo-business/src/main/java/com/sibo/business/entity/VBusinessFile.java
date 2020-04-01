package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author liulong
 * @version 2018-10-15 10:55:56
 */
@Table(name = "v_business_file")
public class VBusinessFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "FILE_NAME")
    private String fileName;
	
	    //
    @Column(name = "FILE_PATH")
    private String filePath;
	
	    //
    @Column(name = "BUSSINESS_ID")
    private String bussinessId;
	
	    //
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

	@Column(name = "TYPE")
	private String type;

	/**
	 * 模板code
	 */
	@Column(name = "TEMPLETED_CODE")
	private String templetedCode;
	

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置：
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * 获取：
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * 设置：
	 */
	public void setBussinessId(String bussinessId) {
		this.bussinessId = bussinessId;
	}
	/**
	 * 获取：
	 */
	public String getBussinessId() {
		return bussinessId;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getUpdUserName() {
		return updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTempletedCode() {
		return templetedCode;
	}

	public void setTempletedCode(String templetedCode) {
		this.templetedCode = templetedCode;
	}
}
