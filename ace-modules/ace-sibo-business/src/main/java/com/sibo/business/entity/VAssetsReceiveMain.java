package com.sibo.business.entity;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.sibo.business.feign.DictFeign;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 资产/仪器领用归还主表
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-11-06 11:33:28
 */
@Table(name = "v_assets_receive_main")
public class VAssetsReceiveMain implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //资产ID
    @Column(name = "ASSETS_ID")
    private String assetsId;
	
	    //资产状态（1.领用2.归还 3.归还确认）
    @Column(name = "ASSETS_STATUS")
    private String assetsStatus;
	
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
	
	    //当前状态
    @Column(name = "CURRENT_STATUS")
    private String currentStatus;
	
	    //领用人/借出人
    @Column(name = "RECIPIENT")
    private String recipient;
	
	    //领用时间
    @Column(name = "RECIPIENT_TIME")
    private Date recipientTime;
	
	    //归还人
    @Column(name = "RETURN_PEOPLE")
    private String returnPeople;
	
	    //归还时间
    @Column(name = "RETURN_TIME")
    private Date returnTime;
	
	    //类型（1.资产 2.仪器）
    @Column(name = "TYPE")
    private String type;
	
	    //版本
    @Column(name = "VERSION")
    private String version;

	//确认人
	@Column(name = "CONFIRM_PERSON")
    private String confirmPerson;

	@Column(name = "FACILITY_DESIGNATED_AREA")
	private String facilityDesignatedArea;

	@Column(name = "EQUIPMENT_DEPARTMENT")
	private String equipmentDepartment;

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
	/**
	 * 设置：资产状态（1.领用2.归还 3.归还确认）
	 */
	public void setAssetsStatus(String assetsStatus) {
		this.assetsStatus = assetsStatus;
	}
	/**
	 * 获取：资产状态（1.领用2.归还 3.归还确认）
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
	/**
	 * 设置：当前状态
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	/**
	 * 获取：当前状态
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * 设置：领用人/借出人
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	/**
	 * 获取：领用人/借出人
	 */
	public String getRecipient() {
		return recipient;
	}
	/**
	 * 设置：领用时间
	 */
	public void setRecipientTime(Date recipientTime) {
		this.recipientTime = recipientTime;
	}
	/**
	 * 获取：领用时间
	 */
	public Date getRecipientTime() {
		return recipientTime;
	}
	/**
	 * 设置：归还人
	 */
	public void setReturnPeople(String returnPeople) {
		this.returnPeople = returnPeople;
	}
	/**
	 * 获取：归还人
	 */
	public String getReturnPeople() {
		return returnPeople;
	}
	/**
	 * 设置：归还时间
	 */
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	/**
	 * 获取：归还时间
	 */
	public Date getReturnTime() {
		return returnTime;
	}
	/**
	 * 设置：类型（1.资产 2.仪器）
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型（1.资产 2.仪器）
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：版本
	 */
	public String getVersion() {
		return version;
	}

	public String getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	public String getFacilityDesignatedArea() {
		return facilityDesignatedArea;
	}

	public void setFacilityDesignatedArea(String facilityDesignatedArea) {
		this.facilityDesignatedArea = facilityDesignatedArea;
	}

	public String getEquipmentDepartment() {
		return equipmentDepartment;
	}

	public void setEquipmentDepartment(String equipmentDepartment) {
		this.equipmentDepartment = equipmentDepartment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
