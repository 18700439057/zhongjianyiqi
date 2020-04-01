package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 资产变更记录
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-22 14:22:08
 */
@Table(name = "v_assets_change_records")
public class VAssetsChangeRecords implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSETS_ID")
    private String assetsId;
	
	    //资产状态（1.提交带确认2.确认）
    @Column(name = "ASSETS_STATUS")
    private String assetsStatus;

    //变更主表id
	@Column(name = "ASSETS_CHANGE_MAIN_ID")
	private String assetsChangeMainId;

	/**
	 * 变更版本
	 */
	@Column(name = "VERSION")
    private String version;
	
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
	public void setAssetsId(String assetsId) {
		this.assetsId = assetsId;
	}
	/**
	 * 获取：资产ID
	 */
	public String getAssetsId() {
		return assetsId;
	}
	/**
	 * 设置：资产状态（1.提交带确认2.确认）
	 */
	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
	}
	/**
	 * 获取：资产状态（1.提交带确认2.确认）
	 */
	public String getAssetsStatus() {
		return assetsStatus;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAssetsChangeMainId() {
		return assetsChangeMainId;
	}

	public void setAssetsChangeMainId(String assetsChangeMainId) {
		this.assetsChangeMainId = assetsChangeMainId;
	}
}
