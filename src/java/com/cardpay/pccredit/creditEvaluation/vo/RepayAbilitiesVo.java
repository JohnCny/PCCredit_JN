package com.cardpay.pccredit.creditEvaluation.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepayAbilitiesVo {
	/*"自有资金": 20,
    "配偶年收入": 25,
    "非经营总资产": 300,
    "月利润": 8,
    "申请期限": 2,
    "非经营总负债": -60*/
	
	/**
	 * 自有资金  double 万元
	 */
	@JsonProperty("自有资金")
	private double ownFunds;
	/**
	 * 配偶年收入  double 万元
	 */
	@JsonProperty("配偶年收入")
	private double spouseIncome;
	/**
	 * 非经营总资产  double 万元
	 */
	@JsonProperty("非经营总资产")
	private double totalNonOperatingAssets;
	/**
	 * 月利润  double 万元
	 */
	@JsonProperty("月利润")
	private double  monthlyProfit;
	/**
	 * 申请期限   月 
	 */
	@JsonProperty("申请期限")
	private int applicationPeriod;
	/**
	 * 非经营总负债  double 万元  负值
	 */
	@JsonProperty("非经营总负债")
	private double nonPperatingTotalLiabilities;
	
	public double getOwnFunds() {
		return ownFunds;
	}
	public void setOwnFunds(double ownFunds) {
		this.ownFunds = ownFunds;
	}
	public double getSpouseIncome() {
		return spouseIncome;
	}
	public void setSpouseIncome(double spouseIncome) {
		this.spouseIncome = spouseIncome;
	}
	public double getTotalNonOperatingAssets() {
		return totalNonOperatingAssets;
	}
	public void setTotalNonOperatingAssets(double totalNonOperatingAssets) {
		this.totalNonOperatingAssets = totalNonOperatingAssets;
	}
	public double getMonthlyProfit() {
		return monthlyProfit;
	}
	
	public int getApplicationPeriod() {
		return applicationPeriod;
	}
	public void setApplicationPeriod(int applicationPeriod) {
		this.applicationPeriod = applicationPeriod;
	}
	public void setMonthlyProfit(double monthlyProfit) {
		this.monthlyProfit = monthlyProfit;
	}
	public double getNonPperatingTotalLiabilities() {
		return nonPperatingTotalLiabilities;
	}
	public void setNonPperatingTotalLiabilities(double nonPperatingTotalLiabilities) {
		this.nonPperatingTotalLiabilities = nonPperatingTotalLiabilities;
	}
	
	
}
