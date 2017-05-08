package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
@ModelParam(table = "customerinformation_jz")
public class CustomerLiving extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String houseClasses;
	private String decorateSituation;
	private Date beginDate;
	private String wetherMortgage;
	private String surveyWay;
	private String houseSturcture;
	private String houseAera;
	private String customerId;
	private Date updateDate;
	private Date createDate;
	
	public String getHouseClasses() {
		return houseClasses;
	}
	public void setHouseClasses(String houseClasses) {
		this.houseClasses = houseClasses;
	}
	public String getDecorateSituation() {
		return decorateSituation;
	}
	public void setDecorateSituation(String decorateSituation) {
		this.decorateSituation = decorateSituation;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getWetherMortgage() {
		return wetherMortgage;
	}
	public void setWetherMortgage(String wetherMortgage) {
		this.wetherMortgage = wetherMortgage;
	}
	public String getSurveyWay() {
		return surveyWay;
	}
	public void setSurveyWay(String surveyWay) {
		this.surveyWay = surveyWay;
	}
	public String getHouseSturcture() {
		return houseSturcture;
	}
	public void setHouseSturcture(String houseSturcture) {
		this.houseSturcture = houseSturcture;
	}
	public String getHouseAera() {
		return houseAera;
	}
	public void setHouseAera(String houseAera) {
		this.houseAera = houseAera;
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
