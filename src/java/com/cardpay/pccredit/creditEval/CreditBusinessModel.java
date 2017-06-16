package com.cardpay.pccredit.creditEval;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 四维授信模型参数 
 * @author songchen
 * @time 2017年6月16日14:32:02
 *
 */
@ModelParam(table = "CREDIT_BUSINESS_MODEL",generator=IDType.assigned)
public class CreditBusinessModel extends BusinessModel
{
  private String customerName;
  private String cardId;
  private String badHabit;
  private String badPublicRecord;
  private String industryCategory;
  private String violationLaw;
  private String creditCardOverdueTime;
  private String creditCardOverdueCount;
  private String loanOverdueTime;
  private String loanOverdueCount;
  private String credit;
  private String creditQueryCount;
  private String applyAmount;
  private String annualDisposableCapital;
  private String annualIncome;
  private String ownersEquity;
  private String receivableRatio;
  private String quickRatio;
  private String businessYears;
  private String cooperationYears;
  private String storeSituation;
  private String mortgageRemaining;
  private String age;
  private String sex;
  private String education;
  private String residence;
  private String marriage;
  private String children;
  private String spouse;
  private String ownedPropertyQuantity;
  private String mortgagePropertyQuantity;
  private String ownedCarsQuantity;
  
  
  //担保
  private String fixedAssets; 			//固定资产         浮点数，单位：元
  private String liquidAssets; 			//流动资产       浮点数，单位：元
  private String householdIncome; 		//家庭年收入        浮点数，单位：元
  private String contingentLiabilities; //或有负债     浮点数，单位：元
  
  //抵押
  private String assetLiabilityRatio;	// 资产负债率 非负浮点数
  private String otherIncome;		    // 其他工作年收入 非负浮点数
  private String spouseIncome;			// 配偶年收入 非负浮点数
  private String loanBalance;			// 贷款余额 非负浮点数
  private String collateralValuation;	//抵押物估值 非负浮点数
  private String collateralCoefficient;	//抵押物系数 0~1之间，按银行规定取值。一般情况下，房产取0.7    商铺取0.6	
  
  

public String getCardId() {
	return cardId;
}
public void setCardId(String cardId) {
	this.cardId = cardId;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
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
public String getCreditCardOverdueTime() {
	return creditCardOverdueTime;
}
public void setCreditCardOverdueTime(String creditCardOverdueTime) {
	this.creditCardOverdueTime = creditCardOverdueTime;
}
public String getCreditCardOverdueCount() {
	return creditCardOverdueCount;
}
public void setCreditCardOverdueCount(String creditCardOverdueCount) {
	this.creditCardOverdueCount = creditCardOverdueCount;
}
public String getLoanOverdueTime() {
	return loanOverdueTime;
}
public void setLoanOverdueTime(String loanOverdueTime) {
	this.loanOverdueTime = loanOverdueTime;
}
public String getLoanOverdueCount() {
	return loanOverdueCount;
}
public void setLoanOverdueCount(String loanOverdueCount) {
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
public String getApplyAmount() {
	return applyAmount;
}
public void setApplyAmount(String applyAmount) {
	this.applyAmount = applyAmount;
}
public String getAnnualDisposableCapital() {
	return annualDisposableCapital;
}
public void setAnnualDisposableCapital(String annualDisposableCapital) {
	this.annualDisposableCapital = annualDisposableCapital;
}
public String getAnnualIncome() {
	return annualIncome;
}
public void setAnnualIncome(String annualIncome) {
	this.annualIncome = annualIncome;
}
public String getOwnersEquity() {
	return ownersEquity;
}
public void setOwnersEquity(String ownersEquity) {
	this.ownersEquity = ownersEquity;
}
public String getReceivableRatio() {
	return receivableRatio;
}
public void setReceivableRatio(String receivableRatio) {
	this.receivableRatio = receivableRatio;
}
public String getQuickRatio() {
	return quickRatio;
}
public void setQuickRatio(String quickRatio) {
	this.quickRatio = quickRatio;
}
public String getBusinessYears() {
	return businessYears;
}
public void setBusinessYears(String businessYears) {
	this.businessYears = businessYears;
}
public String getCooperationYears() {
	return cooperationYears;
}
public void setCooperationYears(String cooperationYears) {
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
public String getAge() {
	return age;
}
public void setAge(String age) {
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
public String getFixedAssets() {
	return fixedAssets;
}
public void setFixedAssets(String fixedAssets) {
	this.fixedAssets = fixedAssets;
}
public String getLiquidAssets() {
	return liquidAssets;
}
public void setLiquidAssets(String liquidAssets) {
	this.liquidAssets = liquidAssets;
}
public String getHouseholdIncome() {
	return householdIncome;
}
public void setHouseholdIncome(String householdIncome) {
	this.householdIncome = householdIncome;
}
public String getContingentLiabilities() {
	return contingentLiabilities;
}
public void setContingentLiabilities(String contingentLiabilities) {
	this.contingentLiabilities = contingentLiabilities;
}
public String getAssetLiabilityRatio() {
	return assetLiabilityRatio;
}
public void setAssetLiabilityRatio(String assetLiabilityRatio) {
	this.assetLiabilityRatio = assetLiabilityRatio;
}
public String getOtherIncome() {
	return otherIncome;
}
public void setOtherIncome(String otherIncome) {
	this.otherIncome = otherIncome;
}
public String getSpouseIncome() {
	return spouseIncome;
}
public void setSpouseIncome(String spouseIncome) {
	this.spouseIncome = spouseIncome;
}
public String getLoanBalance() {
	return loanBalance;
}
public void setLoanBalance(String loanBalance) {
	this.loanBalance = loanBalance;
}
public String getCollateralValuation() {
	return collateralValuation;
}
public void setCollateralValuation(String collateralValuation) {
	this.collateralValuation = collateralValuation;
}
public String getCollateralCoefficient() {
	return collateralCoefficient;
}
public void setCollateralCoefficient(String collateralCoefficient) {
	this.collateralCoefficient = collateralCoefficient;
}

  
  
}
