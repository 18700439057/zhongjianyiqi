package com.sibo.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-10-10 18:02:22
 */
@Table(name = "v_sup_appraise")
public class VSupAppraise implements Serializable {
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
	
	    //产品质量
    @Column(name = "product_quality")
    private String productQuality;
	
	    //资质评价
    @Column(name = "evaluating")
    private String evaluating;
	
	    //评价时间
    @Column(name = "comment_time")
    private Date commentTime;
	
	    //主表id
    @Column(name = "supplier_id")
    private String supplierId;
	
	    //按时交货情况
    @Column(name = "timely_delivery")
    private String timelyDelivery;
	
	    //服务情况
    @Column(name = "servicefall")
    private String servicefall;
	


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
	 * 设置：产品质量
	 */
	public void setProductQuality(String productQuality) {
		this.productQuality = productQuality;
	}
	/**
	 * 获取：产品质量
	 */
	public String getProductQuality() {
		return productQuality;
	}
	/**
	 * 设置：资质评价
	 */
	public void setEvaluating(String evaluating) {
		this.evaluating = evaluating;
	}
	/**
	 * 获取：资质评价
	 */
	public String getEvaluating() {
		return evaluating;
	}
	/**
	 * 设置：评价时间
	 */
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	/**
	 * 获取：评价时间
	 */
	public Date getCommentTime() {
		return commentTime;
	}
	/**
	 * 设置：主表id
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * 获取：主表id
	 */
	public String getSupplierId() {
		return supplierId;
	}
	/**
	 * 设置：按时交货情况
	 */
	public void setTimelyDelivery(String timelyDelivery) {
		this.timelyDelivery = timelyDelivery;
	}
	/**
	 * 获取：按时交货情况
	 */
	public String getTimelyDelivery() {
		return timelyDelivery;
	}
	/**
	 * 设置：服务情况
	 */
	public void setServicefall(String servicefall) {
		this.servicefall = servicefall;
	}
	/**
	 * 获取：服务情况
	 */
	public String getServicefall() {
		return servicefall;
	}

}
