package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计量证书依据表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-09 10:51:38
 */
@Table(name = "v_certificate_based")
public class VCertificateBased implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //证书表ID
    @Column(name = "CERTIFICATE_ID")
    private String certificateId;
	
	    //文件依据名称
    @Column(name = "FILE_BY_NAME")
    private String fileByName;
	
	    //补充说明
    @Column(name = "SUPPLEMENTARY_INSTRUCTION")
    private String supplementaryInstruction;
	
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
	
	    //上传人
    @Column(name = "THE_HEIR")
    private String theHeir;
	
	    //上传时间
    @Column(name = "THE_HEIR_TIME")
    private Date theHeirTime;
	

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
	 * 设置：证书表ID
	 */
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	/**
	 * 获取：证书表ID
	 */
	public String getCertificateId() {
		return certificateId;
	}
	/**
	 * 设置：文件依据名称
	 */
	public void setFileByName(String fileByName) {
		this.fileByName = fileByName;
	}
	/**
	 * 获取：文件依据名称
	 */
	public String getFileByName() {
		return fileByName;
	}
	/**
	 * 设置：补充说明
	 */
	public void setSupplementaryInstruction(String supplementaryInstruction) {
		this.supplementaryInstruction = supplementaryInstruction;
	}
	/**
	 * 获取：补充说明
	 */
	public String getSupplementaryInstruction() {
		return supplementaryInstruction;
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
	 * 设置：上传人
	 */
	public void setTheHeir(String theHeir) {
		this.theHeir = theHeir;
	}
	/**
	 * 获取：上传人
	 */
	public String getTheHeir() {
		return theHeir;
	}
	/**
	 * 设置：上传时间
	 */
	public void setTheHeirTime(Date theHeirTime) {
		this.theHeirTime = theHeirTime;
	}
	/**
	 * 获取：上传时间
	 */
	public Date getTheHeirTime() {
		return theHeirTime;
	}
}
