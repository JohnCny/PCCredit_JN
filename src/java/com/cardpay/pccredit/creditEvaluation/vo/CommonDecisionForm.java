package com.cardpay.pccredit.creditEvaluation.vo;

import com.wicresoft.jrad.base.web.form.BaseForm;


/**
 * 四维模型评估参数 form
 * @author songchen
 * @time 2017年6月27日17:14:18
 */
public class CommonDecisionForm extends BaseForm{
	
	private String userName;// 客户经理userName
	private String userId;// 客户经理id
	private String excelId;// 调查模板id
	private String prodType;// 贷款类型
	
	private String cardNo;
	private String cname;
	private String domicileLocation;
	private String address;
	private String phoneNo;
	private String spouseIdNo;
	private String companyAddress;
	private String industry;
	
	private String modelType;
	// 申请贷款金额，单位：元
    private String applyAmount;
    
	// 贷款用途，0—消费（如旅游、购车、购住房、装修住房、教育），1—经营
	private String loanUse;
	
	// 性别，0—男，1—女
    private String sex;
    
	// 年龄
    private String age;
    
	// 最高学位学历，0—初中及以下，1—高中及技校，2—大学及以上    
	private String education;

	// 户籍所在地，0—本地，1—本省外地，2—省外
    private String residence;

    // 婚姻状况，0—已婚，1—未婚，2—离婚，3—再婚
    private String marriage;
    
	// 子女教育状况，0—无子女，1—上学，2—学龄前，3—工作
    private String childrenEducation;

    // 自有房产数量
    private String ownedPropertyQuantity;

    // 按揭房产数量
    private String mortgagePropertyQuantity;

    // 按揭贷款余额，单位：元
    private String mortgateBalance;
    
	// 自有车辆数量
    private String ownedCarsQuantity;
    
	// 业务年限
    private String businessYears;
    
	// 信用状况-- 1—正常,2—不正常，3—无记录
    private String credit;

	// 信用逾期次数
    private String creditCardOverdueCount;
    
	// 贷款逾期次数
    private String loanOverdueCount;
    
	// 贷款余额，单位：元
    private String loanBalance;
    
	// 担保余额，单位：元
    private String mortgageRemaining;

    // 经济上依赖的人数
    private String numOfEconomicDependence;
    
	// 流动资产，单位：元
    private String liquidAssents;
    
	// 存货，单位：元
    private String stock;
    
	// 固定资产，单位：元
    private String fixedAssents;
    
	// 短期负债，单位：元
    private String shortTermLiabilities;
    
	// 负债总计，单位：元
    private String totalLiabilities;
    
	// 资产总计，单位：元
    private String totalAssents;
    
	// 所有者权益，单位：元
    private String ownersEquity;
    
	// 主营业务收入，单位：元
    private String annualIncome;
    
	// 其他工作年收入，单位：元
    private String otherIncome;

    // 配偶年收入，单位：元
    private String spouseIncome;
    
	// 私人用途分期付款，单位：元
    private String paymentByPrivateUse;
    
	// 年可支配收入，单位：元
    private String annualDisposableCapital;
    
	// 抵质押物品种类，0：商业房产,1：住宅
    private String articleCategory;

    // 抵押物估值，单位：元
    private String collateralValuation;

    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getExcelId() {
		return excelId;
	}

	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}

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
