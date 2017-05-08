package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 客户个人信息
 */
@ModelParam(table = "customerinformation_gr")
public class CustomerPersonal extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String sex;
	private String customerId;
	private String marriage;
	private String domicileplace;
	private String domicileinfo;
	private String fmallyliveplace;
	private String education;
	private String telephone;
	private String mobilephone;
	private Date createdate;
	private Date updatedate;
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getDomicileplace() {
		return domicileplace;
	}
	public void setDomicileplace(String domicileplace) {
		this.domicileplace = domicileplace;
	}
	public String getDomicileinfo() {
		return domicileinfo;
	}
	public void setDomicileinfo(String domicileinfo) {
		this.domicileinfo = domicileinfo;
	}
	public String getFmallyliveplace() {
		return fmallyliveplace;
	}
	public void setFmallyliveplace(String fmallyliveplace) {
		this.fmallyliveplace = fmallyliveplace;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	

}
