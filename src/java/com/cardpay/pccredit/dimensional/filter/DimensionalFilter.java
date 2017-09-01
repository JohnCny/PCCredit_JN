package com.cardpay.pccredit.dimensional.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * Description of DimensionalFilter
 * 
 * @author 王海东
 * @created on 2014-10-20
 * 
 * @version $Id:$
 */
public class DimensionalFilter extends BaseQueryFilter {

	private String customerName;
	
	private String customerManagerName;
	
	private String userId;
	private String appId;;
	
	
	private String startDate;
	private String endDate;
	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
