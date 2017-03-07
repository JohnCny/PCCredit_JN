package com.cardpay.pccredit.creditEvaluation.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditConditionVo {
	/*"婚姻状况": "已婚",
    "最高学位": "本科及以上",
    "家人对申请人评价": "好",
    "邻居对申请人评价": "好",
    "重要联系人对申请人评价": "好",
    "生意关联人对申请人评价": "好",
    "社会公益状况": "有",
    "违法违纪情况": "无",
    "家庭是否和睦": "是",
    "经济上依赖的人数": "1",
    "不良嗜好": "无",
    "不良公共记录": "无",
    "政治情况": "党员",
    "商业保险情况": "有",
    "社会关系": "一般",
    "赡养父母状况": "好",
    "亲属和睦状况": "一般",
    "信用状况": "正常",
    "信用卡逾期情况": "征信报告无逾期",
    "信用卡总数": "2"*/
	
	/**
	 * 婚姻状况
	 */
	@JsonProperty("婚姻状况")
	private String maritalStatus;
	/**
	 * 最高学位
	 */
	@JsonProperty("最高学位")
	private String highestDegree;
	/**
	 * 家人对申请人评价
	 */
	@JsonProperty("家人对申请人评价")
	private String familyEvaluationOfApplicants;
	/**
	 * 邻居对申请人评价
	 */
	@JsonProperty("邻居对申请人评价")
	private String  neighborEvaluation;
	/**
	 * 重要联系人对申请人评价
	 */
	@JsonProperty("重要联系人对申请人评价")
	private String  evaluationOfImportantContactPerson;
	/**
	 * 生意关联人对申请人评价
	 */
	@JsonProperty("生意关联人对申请人评价")
	private String  evaluationOfBusinessAssociates;
	/**
	 * 社会公益状况
	 */
	@JsonProperty("社会公益状况")
	private String  socialWelfareSituation;
	/**
	 * 违法违纪情况
	 */
	@JsonProperty("违法违纪情况")
	private String violationOfLaw;
	/**
	 * 家庭是否和睦
	 */
	@JsonProperty("家庭是否和睦")
	private String familyHarmony;
	/**
	 * 经济上依赖的人数
	 */
	@JsonProperty("经济上依赖的人数")
	private String economicDependence;
	/**
	 * 不良嗜好
	 */
	@JsonProperty("不良嗜好")
	private String  badHabits;
	/**
	 * 不良公共记录
	 */
	@JsonProperty("不良公共记录")
	private String  badPublicRecords;
	/**
	 * 政治情况
	 */
	@JsonProperty("政治情况")
	private String politicalSituation;
	/**
	 * 商业保险情况
	 */
	@JsonProperty("商业保险情况")
	private String commercialInsurance;
	/**
	 * 社会关系
	 */
	@JsonProperty("社会关系")
	private String socialRelations;
	/**
	 * 赡养父母状况
	 */
	@JsonProperty("赡养父母状况")
	private String parentalSupport;
	/**
	 * 亲属和睦状况
	 */
	@JsonProperty("亲属和睦状况")
	private String dfamilyHarmony;
	/**
	 * 信用状况
	 */
	@JsonProperty("信用状况")
	private String creditStatus;
	/**
	 * 信用卡逾期情况
	 */
	@JsonProperty("信用卡逾期情况")
	private String  creditCardOverdue;
	/**
	 * 信用卡总数
	 */
	@JsonProperty("信用卡总数")
	private String creditCardTotalNum;
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getHighestDegree() {
		return highestDegree;
	}
	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}
	public String getFamilyEvaluationOfApplicants() {
		return familyEvaluationOfApplicants;
	}
	public void setFamilyEvaluationOfApplicants(String familyEvaluationOfApplicants) {
		this.familyEvaluationOfApplicants = familyEvaluationOfApplicants;
	}
	public String getNeighborEvaluation() {
		return neighborEvaluation;
	}
	public void setNeighborEvaluation(String neighborEvaluation) {
		this.neighborEvaluation = neighborEvaluation;
	}
	public String getEvaluationOfImportantContactPerson() {
		return evaluationOfImportantContactPerson;
	}
	public void setEvaluationOfImportantContactPerson(
			String evaluationOfImportantContactPerson) {
		this.evaluationOfImportantContactPerson = evaluationOfImportantContactPerson;
	}
	public String getEvaluationOfBusinessAssociates() {
		return evaluationOfBusinessAssociates;
	}
	public void setEvaluationOfBusinessAssociates(
			String evaluationOfBusinessAssociates) {
		this.evaluationOfBusinessAssociates = evaluationOfBusinessAssociates;
	}
	public String getSocialWelfareSituation() {
		return socialWelfareSituation;
	}
	public void setSocialWelfareSituation(String socialWelfareSituation) {
		this.socialWelfareSituation = socialWelfareSituation;
	}
	public String getViolationOfLaw() {
		return violationOfLaw;
	}
	public void setViolationOfLaw(String violationOfLaw) {
		this.violationOfLaw = violationOfLaw;
	}
	public String getFamilyHarmony() {
		return familyHarmony;
	}
	public void setFamilyHarmony(String familyHarmony) {
		this.familyHarmony = familyHarmony;
	}
	public String getEconomicDependence() {
		return economicDependence;
	}
	public void setEconomicDependence(String economicDependence) {
		this.economicDependence = economicDependence;
	}
	public String getBadHabits() {
		return badHabits;
	}
	public void setBadHabits(String badHabits) {
		this.badHabits = badHabits;
	}
	public String getBadPublicRecords() {
		return badPublicRecords;
	}
	public void setBadPublicRecords(String badPublicRecords) {
		this.badPublicRecords = badPublicRecords;
	}
	public String getPoliticalSituation() {
		return politicalSituation;
	}
	public void setPoliticalSituation(String politicalSituation) {
		this.politicalSituation = politicalSituation;
	}
	public String getCommercialInsurance() {
		return commercialInsurance;
	}
	public void setCommercialInsurance(String commercialInsurance) {
		this.commercialInsurance = commercialInsurance;
	}
	public String getSocialRelations() {
		return socialRelations;
	}
	public void setSocialRelations(String socialRelations) {
		this.socialRelations = socialRelations;
	}
	public String getParentalSupport() {
		return parentalSupport;
	}
	public void setParentalSupport(String parentalSupport) {
		this.parentalSupport = parentalSupport;
	}
	public String getDfamilyHarmony() {
		return dfamilyHarmony;
	}
	public void setDfamilyHarmony(String dfamilyHarmony) {
		this.dfamilyHarmony = dfamilyHarmony;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getCreditCardOverdue() {
		return creditCardOverdue;
	}
	public void setCreditCardOverdue(String creditCardOverdue) {
		this.creditCardOverdue = creditCardOverdue;
	}
	public String getCreditCardTotalNum() {
		return creditCardTotalNum;
	}
	public void setCreditCardTotalNum(String creditCardTotalNum) {
		this.creditCardTotalNum = creditCardTotalNum;
	}
	
	
}
