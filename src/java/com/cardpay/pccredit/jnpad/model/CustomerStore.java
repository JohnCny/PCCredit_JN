package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customerinformation_qydp")
public class CustomerStore extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String operationType;
	private String decorateSituation;
	private String houseArea;
	private String housePattern;
	private String methods;
	private String otherInfo;
	private Date beginDate;
	private Date updateDate;
	private Date createDate;
	
	
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getDecorateSituation() {
		return decorateSituation;
	}
	public void setDecorateSituation(String decorateSituation) {
		this.decorateSituation = decorateSituation;
	}
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	public String getHousePattern() {
		return housePattern;
	}
	public void setHousePattern(String housePattern) {
		this.housePattern = housePattern;
	}
	public String getMethods() {
		return methods;
	}
	public void setMethods(String method) {
		this.methods = method;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
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
