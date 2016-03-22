/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 */
public class BatchRunFilter extends BaseQueryFilter{
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
