package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 设备资料
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:35
 */
@Table(name = "v_assets_accessory")
public class VAssetsAccessory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //名称
    @Column(name = "NAME")
    private String name;
	
	    //添加人
    @Column(name = "ADD_PEOPLE")
    private String addPeople;
	
	    //添加时间
    @Column(name = "CRT_TIME")
    private Date addTime;
	
	    //数量
    @Column(name = "AMOUNT")
    private Integer amount;
	
	    //备注
    @Column(name = "REMARK")
    private String remark;
	
	    //关联外键
    @Column(name = "PK_ID")
    private String pkId;

	@Column(name = "TYPE")
	private String type;
	

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
	 * 设置：添加人
	 */
	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}
	/**
	 * 获取：添加人
	 */
	public String getAddPeople() {
		return addPeople;
	}
	/**
	 * 设置：添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取：添加时间
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * 设置：数量
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * 获取：数量
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		if(null == this.remark){
			this.remark = "";
		}
		return remark;
	}
	/**
	 * 设置：关联外键
	 */
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	/**
	 * 获取：关联外键
	 */
	public String getPkId() {
		return pkId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
