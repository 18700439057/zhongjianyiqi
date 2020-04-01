package com.sibo.business.entity;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.sibo.business.feign.DictFeign;
import jdk.nashorn.internal.ir.annotations.Ignore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-10 09:39:26
 */
@Table(name = "v_sup_supplier")
public class VSupSupplier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;

	//创建人
	@Column(name = "crt_user_name")
	private String crtUserName;

	//创建人ID
	@Column(name = "crt_user_id")
	private String crtUserId;

	//创建时间
	@Column(name = "crt_time")
	private Date crtTime;

	//最后更新人
	@Column(name = "upd_user_name")
	private String updUserName;

	//最后更新人ID
	@Column(name = "upd_user_id")
	private String updUserId;

	//最后更新时间
	@Column(name = "upd_time")
	private Date updTime;


	    //分类
    @Column(name = "classify")
	@MergeField(key = "business_supClassify", feign = DictFeign.class, method = "getDictValues")
    private String classify;

	    //名称
    @Column(name = "name")
    private String name;
	
	    //地址
    @Column(name = "address")
    private String address;
	
	    //联系人
    @Column(name = "linkman")
    private String linkman;
	
	    //联系电话
    @Column(name = "phone")
    private String phone;
	
	    //业务范围
    @Column(name = "line_of_business")
    private String lineOfBusiness;


	@Column(name="depart_id")
	private String departId;

	@Column(name = "tenant_id")
	private String tenantId;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	/**
	 * 设置：分类
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}
	/**
	 * 获取：分类
	 */
	public String getClassify() {
		return classify;
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
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：联系人
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	/**
	 * 获取：联系人
	 */
	public String getLinkman() {
		return linkman;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：业务范围
	 */
	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}
	/**
	 * 获取：业务范围
	 */
	public String getLineOfBusiness() {
		return lineOfBusiness;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}
}
