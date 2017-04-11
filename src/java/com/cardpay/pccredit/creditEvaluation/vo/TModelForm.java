package com.cardpay.pccredit.creditEvaluation.vo;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;


/**
 * 四维模型评估参数 form
 * @author songchen
 * @time 2017年3月1日 13:31:40
 */
@ModelParam(table = "T_MODEL_FORM")
public class TModelForm extends  BusinessModel{
	
	
	/*ApplicantInfoVo*/
	private String cardNo;
	private String cname;
	private String sex;
	private String domicileLocation;
	private String address;
	private String phoneNo;
	private String spouseIdNo;
	private String companyAddress;
	private String industry;
	private String    operatingTime;
	
	/*CreditConditionVo*/
	/*private String maritalStatus;
	private String highestDegree;
	private String familyEvaluationOfApplicants;
	private String  neighborEvaluation;
	private String  evaluationOfImportantContactPerson;
	private String  evaluationOfBusinessAssociates;
	private String  socialWelfareSituation;
	private String violationOfLaw;
	private String familyHarmony;
	private String economicDependence;
	private String  badHabits;
	private String  badPublicRecords;
	private String politicalSituation;
	private String commercialInsurance;
	private String socialRelations;
	private String parentalSupport;
	private String dfamilyHarmony;
	private String creditStatus;
	private String  creditCardOverdue;
	private String creditCardTotalNum;*/
	
	/*LivingConditionVo*/
	/*private String dwellingType;
	private String decorationSituation;
	private String housingArea;
	private String ownedPropertyQuantity;
	private String numberOfMortgage;
	private String  housePrice;
	private String  totalPropertyArea;
	private String  numberOfPrivateVehicles;
	private String  numberOfLoans;
	private String  vehiclePrice;
	private String others;
	private String  personalBankAccountBalance;
	private String businessAccountBalance;
	private String totalCreditCardCredit;
	private String averageMonthlyRepaymentAmountOfIncome;
	private String guaranteeForOthers;
	private String theProportionOfTheAmountOfTheSecuredAssets;
	private String securedUse;
	private String guaranteePeriod;*/
	
	/*OperateConditionVo*/
	/*private String organizationType;
	private String operatingArea;
	private String proportionofShareholders;
	private String employees;
	private String businessLicense;
	private String storeType;
	private String shopDecoration;*/
	
	
	/*RepayAbilitiesVo*/
	private String ownFunds;
	private String spouseIncome;
	private String totalNonOperatingAssets;
	private String monthlyProfit;
	private String applicationPeriod;
	private String nonPperateTotalLiabilities;
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
	public String getSpouseIdNo() {
		return spouseIdNo;
	}
	public void setSpouseIdNo(String spouseIdNo) {
		this.spouseIdNo = spouseIdNo;
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
	public String getOperatingTime() {
		return operatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		this.operatingTime = operatingTime;
	}
	public String getOwnFunds() {
		return ownFunds;
	}
	public void setOwnFunds(String ownFunds) {
		this.ownFunds = ownFunds;
	}
	public String getSpouseIncome() {
		return spouseIncome;
	}
	public void setSpouseIncome(String spouseIncome) {
		this.spouseIncome = spouseIncome;
	}
	public String getTotalNonOperatingAssets() {
		return totalNonOperatingAssets;
	}
	public void setTotalNonOperatingAssets(String totalNonOperatingAssets) {
		this.totalNonOperatingAssets = totalNonOperatingAssets;
	}
	public String getMonthlyProfit() {
		return monthlyProfit;
	}
	public void setMonthlyProfit(String monthlyProfit) {
		this.monthlyProfit = monthlyProfit;
	}
	public String getApplicationPeriod() {
		return applicationPeriod;
	}
	public void setApplicationPeriod(String applicationPeriod) {
		this.applicationPeriod = applicationPeriod;
	}
	public String getNonPperateTotalLiabilities() {
		return nonPperateTotalLiabilities;
	}
	public void setNonPperateTotalLiabilities(String nonPperateTotalLiabilities) {
		this.nonPperateTotalLiabilities = nonPperateTotalLiabilities;
	}
	
	
}
