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
	
	
	
	/*2017年4月18日10:10:38*/
	private String spouseIncomeAnnual;  //配偶年收入
    private String cashAndDepositsWithBanks;//现金及银行存款
    private String accountsReceivable;//应收账款
    private String prepaidAccounts;//预付账款
    private String inventory;//存货
    private String shortTermLiabilities;//短期负债
    private String longTermLiabilities;//长期负债
    private String otherLiabilities;//其他负债
    private String contingentLiabilities;//或有负债
    private String ownerEquity;//所有者权益
    private String retainedProfitsMonthly;//月平均净利润
    private String householdExpensesMonthly;//月平均家庭开支
    private String paymentInstalmentsMonthly;//月平均分期付款
    private String otherExpensesMonthly;//月平均其他开支
    private String otherIncomeMonthly;//月平均其他收入
    private String housePrice;//房屋购买价格
    private String capitalAsset;//固定资产
    private String operatingYears;//经营年限
    private String marriage;//婚姻
    private String education;//学历
    
    
    
    
	public String getSpouseIncomeAnnual() {
		return spouseIncomeAnnual;
	}
	public void setSpouseIncomeAnnual(String spouseIncomeAnnual) {
		this.spouseIncomeAnnual = spouseIncomeAnnual;
	}
	public String getCashAndDepositsWithBanks() {
		return cashAndDepositsWithBanks;
	}
	public void setCashAndDepositsWithBanks(String cashAndDepositsWithBanks) {
		this.cashAndDepositsWithBanks = cashAndDepositsWithBanks;
	}
	public String getAccountsReceivable() {
		return accountsReceivable;
	}
	public void setAccountsReceivable(String accountsReceivable) {
		this.accountsReceivable = accountsReceivable;
	}
	public String getPrepaidAccounts() {
		return prepaidAccounts;
	}
	public void setPrepaidAccounts(String prepaidAccounts) {
		this.prepaidAccounts = prepaidAccounts;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getShortTermLiabilities() {
		return shortTermLiabilities;
	}
	public void setShortTermLiabilities(String shortTermLiabilities) {
		this.shortTermLiabilities = shortTermLiabilities;
	}
	public String getLongTermLiabilities() {
		return longTermLiabilities;
	}
	public void setLongTermLiabilities(String longTermLiabilities) {
		this.longTermLiabilities = longTermLiabilities;
	}
	public String getOtherLiabilities() {
		return otherLiabilities;
	}
	public void setOtherLiabilities(String otherLiabilities) {
		this.otherLiabilities = otherLiabilities;
	}
	public String getContingentLiabilities() {
		return contingentLiabilities;
	}
	public void setContingentLiabilities(String contingentLiabilities) {
		this.contingentLiabilities = contingentLiabilities;
	}
	public String getOwnerEquity() {
		return ownerEquity;
	}
	public void setOwnerEquity(String ownerEquity) {
		this.ownerEquity = ownerEquity;
	}
	public String getRetainedProfitsMonthly() {
		return retainedProfitsMonthly;
	}
	public void setRetainedProfitsMonthly(String retainedProfitsMonthly) {
		this.retainedProfitsMonthly = retainedProfitsMonthly;
	}
	public String getHouseholdExpensesMonthly() {
		return householdExpensesMonthly;
	}
	public void setHouseholdExpensesMonthly(String householdExpensesMonthly) {
		this.householdExpensesMonthly = householdExpensesMonthly;
	}
	public String getPaymentInstalmentsMonthly() {
		return paymentInstalmentsMonthly;
	}
	public void setPaymentInstalmentsMonthly(String paymentInstalmentsMonthly) {
		this.paymentInstalmentsMonthly = paymentInstalmentsMonthly;
	}
	public String getOtherExpensesMonthly() {
		return otherExpensesMonthly;
	}
	public void setOtherExpensesMonthly(String otherExpensesMonthly) {
		this.otherExpensesMonthly = otherExpensesMonthly;
	}
	public String getOtherIncomeMonthly() {
		return otherIncomeMonthly;
	}
	public void setOtherIncomeMonthly(String otherIncomeMonthly) {
		this.otherIncomeMonthly = otherIncomeMonthly;
	}
	public String getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(String housePrice) {
		this.housePrice = housePrice;
	}
	public String getCapitalAsset() {
		return capitalAsset;
	}
	public void setCapitalAsset(String capitalAsset) {
		this.capitalAsset = capitalAsset;
	}
	public String getOperatingYears() {
		return operatingYears;
	}
	public void setOperatingYears(String operatingYears) {
		this.operatingYears = operatingYears;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
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
