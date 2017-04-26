package com.cardpay.pccredit.creditEvaluation.vo;

import com.wicresoft.jrad.base.web.form.BaseForm;


/**
 * 四维模型评估参数 form
 * @author songchen
 * @time 2017年3月1日 13:31:40
 */
public class CreditModelModifyForm extends BaseForm{
	
	private String excelId;
	
	private String cardNo;
	private String cname;
	private String domicileLocation;
	private String address;
	private String phoneNo;
	private String spouseIdNo;
	private String companyAddress;
	private String industry;
	
	
	private String badHabit;
	private String badPublicRecord;
	private String industryCategory;
	private String violationLaw;
	private int creditCardOverdueTime;
	private int creditCardOverdueCount;
	private int loanOverdueTime;
	private int loanOverdueCount;
	private String credit;
	private String creditQueryCount;
	private double applyAmoun;
	private double annualDisposableCapital;
	private double annualIncome;
	private double ownersEquity;
	private double receivableRatio;
	private double quickRatio;
	private int businessYears;
	private int cooperationYears;
	private String storeSituation;
	private String mortgageRemaining;
	private int age;
	private String sex;
	private String education;
	private String residence;
	private String marriage;
	private String children;
	private String spouse;
	private String ownedPropertyQuantity;
	private String mortgagePropertyQuantity;
	private String ownedCarsQuantity;
	
	public String getExcelId() {
		return excelId;
	}
	public void setExcelId(String excelId) {
		this.excelId = excelId;
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
	public String getBadHabit() {
		return badHabit;
	}
	public void setBadHabit(String badHabit) {
		this.badHabit = badHabit;
	}
	public String getBadPublicRecord() {
		return badPublicRecord;
	}
	public void setBadPublicRecord(String badPublicRecord) {
		this.badPublicRecord = badPublicRecord;
	}
	public String getIndustryCategory() {
		return industryCategory;
	}
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
	public String getViolationLaw() {
		return violationLaw;
	}
	public void setViolationLaw(String violationLaw) {
		this.violationLaw = violationLaw;
	}
	public int getCreditCardOverdueTime() {
		return creditCardOverdueTime;
	}
	public void setCreditCardOverdueTime(int creditCardOverdueTime) {
		this.creditCardOverdueTime = creditCardOverdueTime;
	}
	public int getCreditCardOverdueCount() {
		return creditCardOverdueCount;
	}
	public void setCreditCardOverdueCount(int creditCardOverdueCount) {
		this.creditCardOverdueCount = creditCardOverdueCount;
	}
	public int getLoanOverdueTime() {
		return loanOverdueTime;
	}
	public void setLoanOverdueTime(int loanOverdueTime) {
		this.loanOverdueTime = loanOverdueTime;
	}
	public int getLoanOverdueCount() {
		return loanOverdueCount;
	}
	public void setLoanOverdueCount(int loanOverdueCount) {
		this.loanOverdueCount = loanOverdueCount;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getCreditQueryCount() {
		return creditQueryCount;
	}
	public void setCreditQueryCount(String creditQueryCount) {
		this.creditQueryCount = creditQueryCount;
	}
	
	public double getApplyAmoun() {
		return applyAmoun;
	}
	public void setApplyAmoun(double applyAmoun) {
		this.applyAmoun = applyAmoun;
	}
	public double getAnnualDisposableCapital() {
		return annualDisposableCapital;
	}
	public void setAnnualDisposableCapital(double annualDisposableCapital) {
		this.annualDisposableCapital = annualDisposableCapital;
	}
	public double getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}
	public double getOwnersEquity() {
		return ownersEquity;
	}
	public void setOwnersEquity(double ownersEquity) {
		this.ownersEquity = ownersEquity;
	}
	public double getReceivableRatio() {
		return receivableRatio;
	}
	public void setReceivableRatio(double receivableRatio) {
		this.receivableRatio = receivableRatio;
	}
	public double getQuickRatio() {
		return quickRatio;
	}
	public void setQuickRatio(double quickRatio) {
		this.quickRatio = quickRatio;
	}
	public int getBusinessYears() {
		return businessYears;
	}
	public void setBusinessYears(int businessYears) {
		this.businessYears = businessYears;
	}
	public int getCooperationYears() {
		return cooperationYears;
	}
	public void setCooperationYears(int cooperationYears) {
		this.cooperationYears = cooperationYears;
	}
	public String getStoreSituation() {
		return storeSituation;
	}
	public void setStoreSituation(String storeSituation) {
		this.storeSituation = storeSituation;
	}
	public String getMortgageRemaining() {
		return mortgageRemaining;
	}
	public void setMortgageRemaining(String mortgageRemaining) {
		this.mortgageRemaining = mortgageRemaining;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public String getSpouse() {
		return spouse;
	}
	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}
	public String getOwnedPropertyQuantity() {
		return ownedPropertyQuantity;
	}
	public void setOwnedPropertyQuantity(String ownedPropertyQuantity) {
		this.ownedPropertyQuantity = ownedPropertyQuantity;
	}
	public String getMortgagePropertyQuantity() {
		return mortgagePropertyQuantity;
	}
	public void setMortgagePropertyQuantity(String mortgagePropertyQuantity) {
		this.mortgagePropertyQuantity = mortgagePropertyQuantity;
	}
	public String getOwnedCarsQuantity() {
		return ownedCarsQuantity;
	}
	public void setOwnedCarsQuantity(String ownedCarsQuantity) {
		this.ownedCarsQuantity = ownedCarsQuantity;
	}
	
	
    
	
	
}
