package com.cardpay.pccredit.report.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class ReportFilter extends BaseQueryFilter {
	private String certiCode;//客户证件号码
	private String custManagerName;//所属客户经理
	private String state;//贷款状态
	private String orgName;//所属机构
	
	public String getCertiCode() {
		return certiCode;
	}
	public void setCertiCode(String certiCode) {
		this.certiCode = certiCode;
	}
	public String getCustManagerName() {
		return custManagerName;
	}
	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}
