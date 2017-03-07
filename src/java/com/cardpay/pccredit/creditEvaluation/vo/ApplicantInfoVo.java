package com.cardpay.pccredit.creditEvaluation.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicantInfoVo {
	/*"身份证号": "62010219860718003x",
    "姓名": "张钰森",
    "性别": "男",
    "户籍所在地": "陕西西安",
    "详细地址": "济南市市中区济南大学计算机系100号",
    "电话": "13800138000",
    "配偶身份证号": "130102197707210039",
    "店铺/企业地址": "济南市历下区会展路225号",
    "所属行业": "网店经营",
    "经营时间": 3*/
	
	/**
	 * 身份证号
	 */
	@JsonProperty("身份证号")
	private String cardNo;
	/**
	 * 姓名
	 */
	@JsonProperty("姓名")
	private String cname;
	/**
	 * 性别
	 */
	@JsonProperty("性别")
	private String sex;
	/**
	 * 户籍所在地
	 */
	@JsonProperty("户籍所在地")
	private String domicileLocation;
	/**
	 * 详细地址
	 */
	@JsonProperty("详细地址")
	private String address;
	/**
	 * 电话
	 */
	@JsonProperty("电话")
	private String phoneNo;
	/**
	 * 配偶身份证号
	 */
	@JsonProperty("配偶身份证号")
	private String spouseIdNo;
	/**
	 * 店铺/企业地址
	 */
	@JsonProperty("店铺/企业地址")
	private String companyAddress;
	/**
	 * 所属行业
	 */
	@JsonProperty("所属行业")
	private String industry;
	/**
	 * 经营时间   int型
	 */
	@JsonProperty("经营时间")
	private int    operatingTime;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getDomicileLocation() {
		return domicileLocation;
	}
	public void setDomicileLocation(String domicileLocation) {
		this.domicileLocation = domicileLocation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public int getOperatingTime() {
		return operatingTime;
	}
	public void setOperatingTime(int operatingTime) {
		this.operatingTime = operatingTime;
	}
	public String getSpouseIdNo() {
		return spouseIdNo;
	}
	public void setSpouseIdNo(String spouseIdNo) {
		this.spouseIdNo = spouseIdNo;
	}
	
	
}
