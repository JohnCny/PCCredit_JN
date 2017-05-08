package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 客户房产信息
 */
@ModelParam(table = "customerinformation_fc")
public class CustomerHouse extends BaseModel{
	
	
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String houseAddress;
	private String houseArea;
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
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
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
