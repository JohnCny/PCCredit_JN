package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customerinformation_qyxx")
public class CustomerCompanyInfo extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String companyName;
	private String organizationPattern;
	private String representative;
	private String realControl;
	private String stockSituation;
	private String businessLicense;
	private Date  beginDate;
	private String plantingYear;
	private String adress;
	private String telephone;
	private Date updateDate;
	private Date createDate;
	
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationPattern() {
		return organizationPattern;
	}
	public void setOrganizationPattern(String organizationPattern) {
		this.organizationPattern = organizationPattern;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getRealControl() {
		return realControl;
	}
	public void setRealControl(String realControl) {
		this.realControl = realControl;
	}
	public String getStockSituation() {
		return stockSituation;
	}
	public void setStockSituation(String stockSituation) {
		this.stockSituation = stockSituation;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getPlantingYear() {
		return plantingYear;
	}
	public void setPlantingYear(String plantingYear) {
		this.plantingYear = plantingYear;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
