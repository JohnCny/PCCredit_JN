package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "manager_meeting")
public class ManagerMeetingInfo extends BaseModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String managerId;
	private String managerName;
	private String customerName;
	private Date internalAuditDate;
	private String internalAuditMember;
	private Date registerMeetingDate;
	private String numberOfTimes;
	private String status;
	private Date createDate;
	private Date modifyDate;
	
	
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getInternalAuditDate() {
		return internalAuditDate;
	}
	public void setInternalAuditDate(Date internalAuditDate) {
		this.internalAuditDate = internalAuditDate;
	}
	public String getInternalAuditMember() {
		return internalAuditMember;
	}
	public void setInternalAuditMember(String internalAuditMember) {
		this.internalAuditMember = internalAuditMember;
	}
	public Date getRegisterMeetingDate() {
		return registerMeetingDate;
	}
	public void setRegisterMeetingDate(Date registerMeetingDate) {
		this.registerMeetingDate = registerMeetingDate;
	}
	public String getNumberOfTimes() {
		return numberOfTimes;
	}
	public void setNumberOfTimes(String numberOfTimes) {
		this.numberOfTimes = numberOfTimes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
