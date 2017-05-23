package com.cardpay.pccredit.manager.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class ManagerMeetingFilter extends BaseQueryFilter{
	private String id;
	private String managerId;
	private Date registerMeetingDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public Date getRegisterMeetingDate() {
		return registerMeetingDate;
	}
	public void setRegisterMeetingDate(Date registerMeetingDate) {
		this.registerMeetingDate = registerMeetingDate;
	}
}
