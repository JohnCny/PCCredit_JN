package com.cardpay.pccredit.manager.filter;

import java.util.Date;
import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class StandingBookFilter extends BaseQueryFilter{
	
	private Date startDate; 
	private Date endDate;
	private String managerId;
	private String customerName;
	
	private String state;
	private int states;
	
	private List<StandingBookFilter> ids;
	
	
	public List<StandingBookFilter> getIds() {
		return ids;
	}
	public void setIds(List<StandingBookFilter> ids) {
		this.ids = ids;
	}
	
	public int getStates() {
		return states;
	}
	public void setStates(int states) {
		this.states = states;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	} 

	
}
