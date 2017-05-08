package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customerinformation_qyyw")
public class CustomerCompanyBusiness extends BaseModel{

	/**
	 * 企业业务信息
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String businessLine;
	private String businessModel;
	private String orgainizationalStructrue;
	private String businessProcess;
	private Date updateDate;
	private Date createDate;
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBusinessLine() {
		return businessLine;
	}
	public void setBusinessLine(String businessLine) {
		this.businessLine = businessLine;
	}
	public String getBusinessModel() {
		return businessModel;
	}
	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}
	public String getOrgainizationalStructrue() {
		return orgainizationalStructrue;
	}
	public void setOrgainizationalStructrue(String orgainizationalStructrue) {
		this.orgainizationalStructrue = orgainizationalStructrue;
	}
	public String getBusinessProcess() {
		return businessProcess;
	}
	public void setBusinessProcess(String businessProcess) {
		this.businessProcess = businessProcess;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
