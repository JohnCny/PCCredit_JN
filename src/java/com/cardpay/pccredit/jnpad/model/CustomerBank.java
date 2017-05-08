package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customerinformation_qykh")
public class CustomerBank extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String openBank;
	private String accuont;
	private Date createDate;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOpenBank() {
		return openBank;
	}
	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}
	public String getAccuont() {
		return accuont;
	}
	public void setAccuont(String accuont) {
		this.accuont = accuont;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

}
