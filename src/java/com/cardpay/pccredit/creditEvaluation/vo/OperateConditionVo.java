package com.cardpay.pccredit.creditEvaluation.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperateConditionVo {
	  /*  "组织类型": "个体",
	    "经营场所面积": "100-200",
	    "股东占比情况": "30%-50%",
	    "雇员人数": "10-30",
	    "营业执照": "有",
	    "店铺类型": "自有产权",
	    "店铺装修情况": "好"*/
	/**
	 * 组织类型
	 */
	@JsonProperty("组织类型")
	private String organizationType;
	/**
	 * 经营场所面积
	 */
	@JsonProperty("经营场所面积")
	private String operatingArea;
	/**
	 * 股东占比情况
	 */
	@JsonProperty("股东占比情况")
	private String proportionofShareholders;
	/**
	 * 雇员人数
	 */
	@JsonProperty("雇员人数")
	private String employees;
	/**
	 * 营业执照
	 */
	@JsonProperty("营业执照")
	private String businessLicense;
	/**
	 * 店铺类型
	 */
	@JsonProperty("店铺类型")
	private String storeType;
	/**
	 * 店铺装修情况
	 */
	@JsonProperty("店铺装修情况")
	private String shopDecoration;
	
	public String getOrganizationType() {
		return organizationType;
	}
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	public String getOperatingArea() {
		return operatingArea;
	}
	public void setOperatingArea(String operatingArea) {
		this.operatingArea = operatingArea;
	}
	public String getProportionofShareholders() {
		return proportionofShareholders;
	}
	public void setProportionofShareholders(String proportionofShareholders) {
		this.proportionofShareholders = proportionofShareholders;
	}
	public String getEmployees() {
		return employees;
	}
	public void setEmployees(String employees) {
		this.employees = employees;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getShopDecoration() {
		return shopDecoration;
	}
	public void setShopDecoration(String shopDecoration) {
		this.shopDecoration = shopDecoration;
	}
	
	
}
