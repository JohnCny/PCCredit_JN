package com.cardpay.pccredit.jnpad.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class ModelFormFilter extends BaseQueryFilter{
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
