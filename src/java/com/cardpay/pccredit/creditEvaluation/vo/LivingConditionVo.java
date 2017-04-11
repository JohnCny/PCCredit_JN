package com.cardpay.pccredit.creditEvaluation.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LivingConditionVo {
	/*"居住类型": "自有",
    "装修情况": "好",
    "住房面积": "≤100",
    "自有房产数量": 3,
    "按揭房产数量": 2,
    "房产总价": "100-300",
    "自有房产总面积": "100-200",
    "自有车辆数量": 3,
    "贷款车辆数量": 2,
    "车辆总价": "50-100",
    "除经营生意外是否有其他工作": "是",
    "个人银行帐户余额": "300-500",
    "生意帐户余额": "500-1000",
    "信用卡授信总额": "5-10",
    "月平均还款金额占收入比例": "≥70%",
    "是否为他人担保": "是",
    "担保金额占本人自有资产比例": "10%-50%",
    "担保用途": "朋友买房/车/消费",
    "担保期限": "3-5"*/
	/**
	 * 居住类型
	 */
	@JsonProperty("居住类型")
	private String dwellingType;
	
	/**
	 * 装修情况
	 */
	@JsonProperty("装修情况")
	private String decorationSituation;
	
	/**
	 * 住房面积   //平方米
	 */
	@JsonProperty("住房面积")
	private String housingArea;
	
	/**
	 * 自有房产数量
	 */
	@JsonProperty("自有房产数量")
	private String ownedPropertyQuantity;
	
	/**
	 * 按揭房产数量
	 */
	@JsonProperty("按揭房产数量")
	private String numberOfMortgage;
	
	/**
	 * 房产总价   //万元
	 */
	@JsonProperty("房产总价")
	private String  housePrice;
	
	/**
	 * 自有房产总面积   //平方米
	 */
	@JsonProperty("自有房产总面积")
	private String  totalPropertyArea;
	
	/**
	 * 自有车辆数量
	 */
	@JsonProperty("自有车辆数量")
	private String  numberOfPrivateVehicles;
	
	/**
	 * 贷款车辆数量
	 */
	@JsonProperty("贷款车辆数量")
	private String  numberOfLoans;
	
	/**
	 * 车辆总价  //万元
	 */
	@JsonProperty("车辆总价")
	private String  vehiclePrice;
	
	/**
	 * 除经营生意外是否有其他工作
	 */
	@JsonProperty("除经营生意外是否有其他工作")
	private String others;
	
	/**
	 * 个人银行帐户余额 //万元
	 */
	@JsonProperty("个人银行帐户余额")
	private String  personalBankAccountBalance;
	
	/**
	 * 生意帐户余额 //万元
	 */
	@JsonProperty("生意帐户余额")
	private String businessAccountBalance;
	
	/**
	 * 信用卡授信总额  //万元
	 */
	@JsonProperty("信用卡授信总额")
	private String totalCreditCardCredit;
	
	/**
	 * 月平均还款金额占收入比例
	 */
	@JsonProperty("月平均还款金额占收入比例")
	private String averageMonthlyRepaymentAmountOfIncome;
	
	/**
	 * 是否为他人担保
	 */
	@JsonProperty("是否为他人担保")
	private String guaranteeForOthers;
	
	/**
	 * 担保金额占本人自有资产比例
	 */
	@JsonProperty("担保金额占本人自有资产比例")
	private String theProportionOfTheAmountOfTheSecuredAssets;
	
	/**
	 * 担保用途
	 */
	@JsonProperty("担保用途")
	private String securedUse;
	
	/**
	 * 担保期限   //年
	 */
	@JsonProperty("担保期限")
	private String guaranteePeriod;

	public String getDwellingType() {
		return dwellingType;
	}

	public void setDwellingType(String dwellingType) {
		this.dwellingType = dwellingType;
	}

	public String getDecorationSituation() {
		return decorationSituation;
	}

	public void setDecorationSituation(String decorationSituation) {
		this.decorationSituation = decorationSituation;
	}

	public String getHousingArea() {
		return housingArea;
	}

	public void setHousingArea(String housingArea) {
		this.housingArea = housingArea;
	}

	public String getOwnedPropertyQuantity() {
		return ownedPropertyQuantity;
	}

	public void setOwnedPropertyQuantity(String ownedPropertyQuantity) {
		this.ownedPropertyQuantity = ownedPropertyQuantity;
	}

	public String getNumberOfMortgage() {
		return numberOfMortgage;
	}

	public void setNumberOfMortgage(String numberOfMortgage) {
		this.numberOfMortgage = numberOfMortgage;
	}

	public String getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(String housePrice) {
		this.housePrice = housePrice;
	}

	public String getTotalPropertyArea() {
		return totalPropertyArea;
	}

	public void setTotalPropertyArea(String totalPropertyArea) {
		this.totalPropertyArea = totalPropertyArea;
	}

	public String getNumberOfPrivateVehicles() {
		return numberOfPrivateVehicles;
	}

	public void setNumberOfPrivateVehicles(String numberOfPrivateVehicles) {
		this.numberOfPrivateVehicles = numberOfPrivateVehicles;
	}

	public String getNumberOfLoans() {
		return numberOfLoans;
	}

	public void setNumberOfLoans(String numberOfLoans) {
		this.numberOfLoans = numberOfLoans;
	}

	public String getVehiclePrice() {
		return vehiclePrice;
	}

	public void setVehiclePrice(String vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getPersonalBankAccountBalance() {
		return personalBankAccountBalance;
	}

	public void setPersonalBankAccountBalance(String personalBankAccountBalance) {
		this.personalBankAccountBalance = personalBankAccountBalance;
	}

	public String getBusinessAccountBalance() {
		return businessAccountBalance;
	}

	public void setBusinessAccountBalance(String businessAccountBalance) {
		this.businessAccountBalance = businessAccountBalance;
	}

	public String getTotalCreditCardCredit() {
		return totalCreditCardCredit;
	}

	public void setTotalCreditCardCredit(String totalCreditCardCredit) {
		this.totalCreditCardCredit = totalCreditCardCredit;
	}

	public String getAverageMonthlyRepaymentAmountOfIncome() {
		return averageMonthlyRepaymentAmountOfIncome;
	}

	public void setAverageMonthlyRepaymentAmountOfIncome(
			String averageMonthlyRepaymentAmountOfIncome) {
		this.averageMonthlyRepaymentAmountOfIncome = averageMonthlyRepaymentAmountOfIncome;
	}

	public String getGuaranteeForOthers() {
		return guaranteeForOthers;
	}

	public void setGuaranteeForOthers(String guaranteeForOthers) {
		this.guaranteeForOthers = guaranteeForOthers;
	}

	public String getTheProportionOfTheAmountOfTheSecuredAssets() {
		return theProportionOfTheAmountOfTheSecuredAssets;
	}

	public void setTheProportionOfTheAmountOfTheSecuredAssets(
			String theProportionOfTheAmountOfTheSecuredAssets) {
		this.theProportionOfTheAmountOfTheSecuredAssets = theProportionOfTheAmountOfTheSecuredAssets;
	}

	public String getSecuredUse() {
		return securedUse;
	}

	public void setSecuredUse(String securedUse) {
		this.securedUse = securedUse;
	}

	public String getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(String guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}
	
	
}
