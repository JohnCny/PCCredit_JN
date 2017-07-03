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
	
	private String userId;//客户经理id
	

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
