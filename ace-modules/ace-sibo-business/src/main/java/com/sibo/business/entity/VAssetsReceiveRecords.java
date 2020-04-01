package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 资产领用记录
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-22 14:27:58
 */
@Table(name = "v_assets_receive_records")
public class VAssetsReceiveRecords implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSETS_ID")
    private String assetsId;

	/**
	 * 领用归还主表id
	 */
	@Column(name = "ASSETS_RECEIVE_MAIN_ID")
	private String assetsReceiveMainId;
	
	    //资产类型（1.领用2.归还）
    @Column(name = "ASSETS_STATUS")
    private String assetsStatus;

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
	 * 当前状态
	 */
	@Column(name = "CURRENT_STATUS")
	private String currentStatus;

	/**
	 * 领用人
	 */
	@Column(name = "RECIPIENT")
	private String recipient;

	/**
	 * 领用时间
	 */
	@Column(name = "RECIPIENT_TIME")
	private Date recipientTime;

	/**
	 * 归还人
	 */
	@Column(name = "RETURN_PEOPLE")
	private String returnPeople;

	/**
	 * 归还时间
	 */
	@Column(name = "RETURN_TIME")
	private Date returnTime;

	@Column(name = "VERSION")
	private Integer version;

	/**
	 * 是否系统自动确认
	 */
	@Column(name = "AUTOCONFIRM")
	private String autoConfirm;

	@Column(name = "IS_READ")
	private String isRead;

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

	public String getAssetsStatus() {
		return assetsStatus;
	}

	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
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

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Date getRecipientTime() {
		return recipientTime;
	}

	public void setRecipientTime(Date recipientTime) {
		this.recipientTime = recipientTime;
	}

	public String getReturnPeople() {
		return returnPeople;
	}

	public void setReturnPeople(String returnPeople) {
		this.returnPeople = returnPeople;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getAssetsReceiveMainId() {
		return assetsReceiveMainId;
	}

	public void setAssetsReceiveMainId(String assetsReceiveMainId) {
		this.assetsReceiveMainId = assetsReceiveMainId;
	}

	public String getAutoConfirm() {
		return autoConfirm;
	}

	public void setAutoConfirm(String autoConfirm) {
		this.autoConfirm = autoConfirm;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
