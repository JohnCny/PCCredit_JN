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
@ModelParam(table = "T_COMMON_DECISION_MODEL",generator=IDType.assigned)
public class CommonDecisionModel extends BusinessModel
{
    private String customerName;
    private String cardId;
    private String excelId;
  
  
	private String applyAmount;
	private String loanUse;
	private String sex;
	private String age;
	private String education;
	private String residence;
	private String marriage;
	private String childrenEducation;
	private String ownedPropertyQuantity;
	private String mortgagePropertyQuantity;
	private String mortgateBalance;
	private String ownedCarsQuantity;
	private String businessYears;
	private String credit;
	private String creditCardOverdueCount;
	private String loanOverdueCount;
	private String loanBalance;
	private String mortgageRemaining;
	private String numOfEconomicDependence;
	private String liquidAssents;
	private String stock;
	private String fixedAssents;
	private String shortTermLiabilities;
	private String totalLiabilities;
	private String totalAssents;
	private String ownersEquity;
	private String annualIncome;
	private String otherIncome;
	private String spouseIncome;
	private String paymentByPrivateUse;
	private String annualDisposableCapital;
	private String articleCategory;
	private String collateralValuation;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getExcelId() {
		return excelId;
	}
	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getLoanUse() {
		return loanUse;
	}
	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
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
	public String getChildrenEducation() {
		return childrenEducation;
	}
	public void setChildrenEducation(String childrenEducation) {
		this.childrenEducation = childrenEducation;
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
	public String getMortgateBalance() {
		return mortgateBalance;
	}
	public void setMortgateBalance(String mortgateBalance) {
		this.mortgateBalance = mortgateBalance;
	}
	public String getOwnedCarsQuantity() {
		return ownedCarsQuantity;
	}
	public void setOwnedCarsQuantity(String ownedCarsQuantity) {
		this.ownedCarsQuantity = ownedCarsQuantity;
	}
	public String getBusinessYears() {
		return businessYears;
	}
	public void setBusinessYears(String businessYears) {
		this.businessYears = businessYears;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getCreditCardOverdueCount() {
		return creditCardOverdueCount;
	}
	public void setCreditCardOverdueCount(String creditCardOverdueCount) {
		this.creditCardOverdueCount = creditCardOverdueCount;
	}
	public String getLoanOverdueCount() {
		return loanOverdueCount;
	}
	public void setLoanOverdueCount(String loanOverdueCount) {
		this.loanOverdueCount = loanOverdueCount;
	}
	public String getLoanBalance() {
		return loanBalance;
	}
	public void setLoanBalance(String loanBalance) {
		this.loanBalance = loanBalance;
	}
	public String getMortgageRemaining() {
		return mortgageRemaining;
	}
	public void setMortgageRemaining(String mortgageRemaining) {
		this.mortgageRemaining = mortgageRemaining;
	}
	public String getNumOfEconomicDependence() {
		return numOfEconomicDependence;
	}
	public void setNumOfEconomicDependence(String numOfEconomicDependence) {
		this.numOfEconomicDependence = numOfEconomicDependence;
	}
	public String getLiquidAssents() {
		return liquidAssents;
	}
	public void setLiquidAssents(String liquidAssents) {
		this.liquidAssents = liquidAssents;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getFixedAssents() {
		return fixedAssents;
	}
	public void setFixedAssents(String fixedAssents) {
		this.fixedAssents = fixedAssents;
	}
	public String getShortTermLiabilities() {
		return shortTermLiabilities;
	}
	public void setShortTermLiabilities(String shortTermLiabilities) {
		this.shortTermLiabilities = shortTermLiabilities;
	}
	public String getTotalLiabilities() {
		return totalLiabilities;
	}
	public void setTotalLiabilities(String totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}
	public String getTotalAssents() {
		return totalAssents;
	}
	public void setTotalAssents(String totalAssents) {
		this.totalAssents = totalAssents;
	}
	public String getOwnersEquity() {
		return ownersEquity;
	}
	public void setOwnersEquity(String ownersEquity) {
		this.ownersEquity = ownersEquity;
	}
	public String getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
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
	public String getPaymentByPrivateUse() {
		return paymentByPrivateUse;
	}
	public void setPaymentByPrivateUse(String paymentByPrivateUse) {
		this.paymentByPrivateUse = paymentByPrivateUse;
	}
	public String getAnnualDisposableCapital() {
		return annualDisposableCapital;
	}
	public void setAnnualDisposableCapital(String annualDisposableCapital) {
		this.annualDisposableCapital = annualDisposableCapital;
	}
	public String getArticleCategory() {
		return articleCategory;
	}
	public void setArticleCategory(String articleCategory) {
		this.articleCategory = articleCategory;
	}
	public String getCollateralValuation() {
		return collateralValuation;
	}
	public void setCollateralValuation(String collateralValuation) {
		this.collateralValuation = collateralValuation;
	}
  
	
	
}
