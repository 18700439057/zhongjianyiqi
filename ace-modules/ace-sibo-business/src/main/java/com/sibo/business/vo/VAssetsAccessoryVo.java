package com.sibo.business.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 设备资料
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-16 14:26:35
 */
@Getter
@Setter
public class VAssetsAccessoryVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    private String id;
	
	    //名称
    private String name;
	
	    //添加人
    private String addPeople;
	
	    //添加时间
    private Date addTime;
	
	    //数量
    private Integer amount;
	
	    //备注
    private String remark;
	
	    //关联外键
    private String pkId;

    private String type;

	//附件上传
	private List<VBusinessFileVo> imageList;
	public VAssetsAccessoryVo() {
	}

	public VAssetsAccessoryVo(String id) {
		this.id = id;
	}

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

	public List<VBusinessFileVo> getImageList() {
		return imageList;
	}

	public void setImageList(List<VBusinessFileVo> imageList) {
		this.imageList = imageList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
