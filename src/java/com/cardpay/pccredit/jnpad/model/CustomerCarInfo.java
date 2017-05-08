package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customerinformation_cc")
public class CustomerCarInfo extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String carVersion;
	private String carNumber;
	private Date monetaryDate;
	private String monetaryAmount;
	private String currentAmount;
	private String getWay;
	private String otherInfo;
	private Date createDate;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCarVersion() {
		return carVersion;
	}
	public void setCarVersion(String carVersion) {
		this.carVersion = carVersion;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public Date getMonetaryDate() {
		return monetaryDate;
	}
	public void setMonetaryDate(Date monetaryDate) {
		this.monetaryDate = monetaryDate;
	}
	public String getMonetaryAmount() {
		return monetaryAmount;
	}
	public void setMonetaryAmount(String monetaryAmount) {
		this.monetaryAmount = monetaryAmount;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	public String getGetWay() {
		return getWay;
	}
	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
