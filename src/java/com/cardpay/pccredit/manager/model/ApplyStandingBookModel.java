package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "ApplyStandingBook")
public class ApplyStandingBookModel extends BaseModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String managerId; 
	private Date applyDate; //申请日期
	private String customerName;//客户姓名
	private String contactMethod;//联系方式
	private String homeAddress;//家庭住址
	private String workPlace;//营业实体/工作单位
	private String product;//贷款产品
	private String applyAmount;//申请金额
	private String applyDeadline;//申请期限
	private String proceedUse;//贷款用途
	private String financingExperience;//是否有融资经历（贷款信息）
	private String managerName;//接待客户经理
	private String jurisdiction;//所属管辖行
	private String remark;//备注
	private Date createdTime;
	
	private String vistedId;//拜访id
	
	
	public String getVistedId() {
		return vistedId;
	}
	public void setVistedId(String vistedId) {
		this.vistedId = vistedId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactMethod() {
		return contactMethod;
	}
	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getApplyDeadline() {
		return applyDeadline;
	}
	public void setApplyDeadline(String applyDeadline) {
		this.applyDeadline = applyDeadline;
	}
	public String getProceedUse() {
		return proceedUse;
	}
	public void setProceedUse(String proceedUse) {
		this.proceedUse = proceedUse;
	}
	public String getFinancingExperience() {
		return financingExperience;
	}
	public void setFinancingExperience(String financingExperience) {
		this.financingExperience = financingExperience;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
	

}
