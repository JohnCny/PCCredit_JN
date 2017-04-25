package com.cardpay.pccredit.creditEvaluation.vo;

import com.wicresoft.jrad.base.web.form.BaseForm;


/**
 * 四维模型评估参数 form
 * @author songchen
 * @time 2017年3月1日 13:31:40
 */
public class ModelModifyForm extends BaseForm{
	
	private String excelId;
	
	private String cardNo;
	private String cname;
	private String sex;
	private String domicileLocation;
	private String address;
	private String phoneNo;
	private String spouseIdNo;
	private String companyAddress;
	private String industry;
	
	private double spouseIncomeAnnual;  //配偶年收入
    private double cashAndDepositsWithBanks;//现金及银行存款
    private double accountsReceivable;//应收账款
    private double prepaidAccounts;//预付账款
    private double inventory;//存货
    private double shortTermLiabilities;//短期负债
    private double longTermLiabilities;//长期负债
    private double otherLiabilities;//其他负债
    private double contingentLiabilities;//或有负债
    private double ownerEquity;//所有者权益
    private double retainedProfitsMonthly;//月平均净利润
    private double householdExpensesMonthly;//月平均家庭开支
    private double paymentInstalmentsMonthly;//月平均分期付款
    private double otherExpensesMonthly;//月平均其他开支
    private double otherIncomeMonthly;//月平均其他收入
    private double housePrice;//房屋购买价格
    private double capitalAsset;//固定资产
    private double operatingYears;//经营年限
    private double marriage;//婚姻
    private double education;//学历
    
    
    private String prodType;//产品类型
    
    
    
    
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
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
	public String getExcelId() {
		return excelId;
	}
	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}
	public double getSpouseIncomeAnnual() {
		return spouseIncomeAnnual;
	}
	public void setSpouseIncomeAnnual(double spouseIncomeAnnual) {
		this.spouseIncomeAnnual = spouseIncomeAnnual;
	}
	public double getCashAndDepositsWithBanks() {
		return cashAndDepositsWithBanks;
	}
	public void setCashAndDepositsWithBanks(double cashAndDepositsWithBanks) {
		this.cashAndDepositsWithBanks = cashAndDepositsWithBanks;
	}
	public double getAccountsReceivable() {
		return accountsReceivable;
	}
	public void setAccountsReceivable(double accountsReceivable) {
		this.accountsReceivable = accountsReceivable;
	}
	public double getPrepaidAccounts() {
		return prepaidAccounts;
	}
	public void setPrepaidAccounts(double prepaidAccounts) {
		this.prepaidAccounts = prepaidAccounts;
	}
	public double getInventory() {
		return inventory;
	}
	public void setInventory(double inventory) {
		this.inventory = inventory;
	}
	public double getShortTermLiabilities() {
		return shortTermLiabilities;
	}
	public void setShortTermLiabilities(double shortTermLiabilities) {
		this.shortTermLiabilities = shortTermLiabilities;
	}
	public double getLongTermLiabilities() {
		return longTermLiabilities;
	}
	public void setLongTermLiabilities(double longTermLiabilities) {
		this.longTermLiabilities = longTermLiabilities;
	}
	public double getOtherLiabilities() {
		return otherLiabilities;
	}
	public void setOtherLiabilities(double otherLiabilities) {
		this.otherLiabilities = otherLiabilities;
	}
	public double getContingentLiabilities() {
		return contingentLiabilities;
	}
	public void setContingentLiabilities(double contingentLiabilities) {
		this.contingentLiabilities = contingentLiabilities;
	}
	public double getOwnerEquity() {
		return ownerEquity;
	}
	public void setOwnerEquity(double ownerEquity) {
		this.ownerEquity = ownerEquity;
	}
	public double getRetainedProfitsMonthly() {
		return retainedProfitsMonthly;
	}
	public void setRetainedProfitsMonthly(double retainedProfitsMonthly) {
		this.retainedProfitsMonthly = retainedProfitsMonthly;
	}
	public double getHouseholdExpensesMonthly() {
		return householdExpensesMonthly;
	}
	public void setHouseholdExpensesMonthly(double householdExpensesMonthly) {
		this.householdExpensesMonthly = householdExpensesMonthly;
	}
	public double getPaymentInstalmentsMonthly() {
		return paymentInstalmentsMonthly;
	}
	public void setPaymentInstalmentsMonthly(double paymentInstalmentsMonthly) {
		this.paymentInstalmentsMonthly = paymentInstalmentsMonthly;
	}
	public double getOtherExpensesMonthly() {
		return otherExpensesMonthly;
	}
	public void setOtherExpensesMonthly(double otherExpensesMonthly) {
		this.otherExpensesMonthly = otherExpensesMonthly;
	}
	public double getOtherIncomeMonthly() {
		return otherIncomeMonthly;
	}
	public void setOtherIncomeMonthly(double otherIncomeMonthly) {
		this.otherIncomeMonthly = otherIncomeMonthly;
	}
	public double getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(double housePrice) {
		this.housePrice = housePrice;
	}
	public double getCapitalAsset() {
		return capitalAsset;
	}
	public void setCapitalAsset(double capitalAsset) {
		this.capitalAsset = capitalAsset;
	}
	public double getOperatingYears() {
		return operatingYears;
	}
	public void setOperatingYears(double operatingYears) {
		this.operatingYears = operatingYears;
	}
	public double getMarriage() {
		return marriage;
	}
	public void setMarriage(double marriage) {
		this.marriage = marriage;
	}
	public double getEducation() {
		return education;
	}
	public void setEducation(double education) {
		this.education = education;
	}
    
    
    
	
	
}
