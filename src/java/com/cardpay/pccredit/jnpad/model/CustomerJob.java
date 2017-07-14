package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customerinformation_gxxx")
public class CustomerJob extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jobAddress;
	private String jobYears;
	private String  jobAmount;
	private String rank;
	private String accumulation;
	private String  accumulationYears;
	private String  social;
	private String  socialYears;
	private String  unitProperty;
	private String  customerId;
	private Date updateDate;
	private Date createDate;
	public String getJobAddress() {
		return jobAddress;
	}
	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}
	public String getJobYears() {
		return jobYears;
	}
	public void setJobYears(String jobYears) {
		this.jobYears = jobYears;
	}
	public String getJobAmount() {
		return jobAmount;
	}
	public void setJobAmount(String jobAmount) {
		this.jobAmount = jobAmount;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getAccumulation() {
		return accumulation;
	}
	public void setAccumulation(String accumulation) {
		this.accumulation = accumulation;
	}
	public String getAccumulationYears() {
		return accumulationYears;
	}
	public void setAccumulationYears(String accumulationYears) {
		this.accumulationYears = accumulationYears;
	}
	public String getSocial() {
		return social;
	}
	public void setSocial(String social) {
		this.social = social;
	}
	public String getSocialYears() {
		return socialYears;
	}
	public void setSocialYears(String socialYears) {
		this.socialYears = socialYears;
	}
	public String getUnitProperty() {
		return unitProperty;
	}
	public void setUnitProperty(String unitProperty) {
		this.unitProperty = unitProperty;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
