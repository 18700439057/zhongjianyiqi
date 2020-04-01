package com.sibo.business.vo;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2018-10-10 09:39:26
 */
public class VSupSupplierVo implements Serializable {

    private static final long serialVersionUID = -6625521051532170772L;

    private String id;

    private String crtUserName;

    //创建人ID
    private String crtUserId;

    //创建时间
    private Date crtTime;

    //最后更新人
    private String updUserName;

    //最后更新人ID
    private String updUserId;

    //最后更新时间
    private Date updTime;


    //分类
    private String classify;

    //名称
    private String name;

    //地址
    private String address;

    //联系人
    private String linkman;

    //联系电话
    private String phone;

    //业务范围
    private String lineOfBusiness;

    private String departId;

    private String tenantId;

    //附件上传
    private List<VBusinessFileVo> imageList;


    public VSupSupplierVo() {
    }
    public VSupSupplierVo(String id) {
        this.id = id;
    }


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

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public List<VBusinessFileVo> getImageList() {
        return imageList;
    }

    public void setImageList(List<VBusinessFileVo> imageList) {
        this.imageList = imageList;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
