package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:50:49
 */
@ModelParam(table = "manager_salary")
public class ManagerSalary extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;
	// 客户经理姓名
	private String managerName;
	// 客户经理奖励激励金额= 绩效工资
	private String rewardIncentiveInformation;
	// 扣除金额
	private String deductAmount;
	// 底薪
	private String basePay;
	// 津贴
	private String allowance;
	// 责任工资
	private String dutySalary;
	// 返还金额
	private String returnPrepareAmount;
	// 新增风险保证金
	private String insertPrepareAmount;
	// 风险准备金余额
	private String riskReserveBalances;
	// 罚款
	private String fine;
	
	private String year;
	
	private String month;
	
	private String describe;
	
	// 个人完成度
	private String competerPet;
	//主管绩效
	private String groupSalary;
	//主调绩效
	private String zdPerform;
	//辅调绩效
	private String fdPerform;
	//管户绩效
	private String ghPerform;
	//审批绩效
	private String spPerform;
	//岗位绩效
	private String gwPerform;
	//完成绩效
	private String competerPerform;
	//团队名
	private String shortName;
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRewardIncentiveInformation() {
		return rewardIncentiveInformation;
	}

	public void setRewardIncentiveInformation(String rewardIncentiveInformation) {
		this.rewardIncentiveInformation = rewardIncentiveInformation;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getBasePay() {
		return basePay;
	}

	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}

	public String getReturnPrepareAmount() {
		return returnPrepareAmount;
	}

	public void setReturnPrepareAmount(String returnPrepareAmount) {
		this.returnPrepareAmount = returnPrepareAmount;
	}

	public String getInsertPrepareAmount() {
		return insertPrepareAmount;
	}

	public void setInsertPrepareAmount(String insertPrepareAmount) {
		this.insertPrepareAmount = insertPrepareAmount;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getRiskReserveBalances() {
		return riskReserveBalances;
	}

	public void setRiskReserveBalances(String riskReserveBalances) {
		this.riskReserveBalances = riskReserveBalances;
	}

	public String getCompeterPet() {
		return competerPet;
	}

	public void setCompeterPet(String competerPet) {
		this.competerPet = competerPet;
	}

	public String getGroupSalary() {
		return groupSalary;
	}

	public void setGroupSalary(String groupSalary) {
		this.groupSalary = groupSalary;
	}

	public String getZdPerform() {
		return zdPerform;
	}

	public void setZdPerform(String zdPerform) {
		this.zdPerform = zdPerform;
	}

	public String getFdPerform() {
		return fdPerform;
	}

	public void setFdPerform(String fdPerform) {
		this.fdPerform = fdPerform;
	}

	public String getGhPerform() {
		return ghPerform;
	}

	public void setGhPerform(String ghPerform) {
		this.ghPerform = ghPerform;
	}

	public String getSpPerform() {
		return spPerform;
	}

	public void setSpPerform(String spPerform) {
		this.spPerform = spPerform;
	}

	public String getGwPerform() {
		return gwPerform;
	}

	public void setGwPerform(String gwPerform) {
		this.gwPerform = gwPerform;
	}

	public String getCompeterPerform() {
		return competerPerform;
	}

	public void setCompeterPerform(String competerPerform) {
		this.competerPerform = competerPerform;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
