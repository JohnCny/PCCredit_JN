package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 放款台账
 */
@ModelParam(table = "LOAN_APPROVED")
public class LoanApproved extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private String customerAge;
	private String customerTele;
	private String customerMarriage;
	private String mateName;
	private String mateIdNo;
	private String mateTele;
	private String homeAdd;
	private String busIndustry;
	private String busName;
	private String busAdd;
	private String prdName;
	private String chiefManager;
	private String supManager;
	private String approvalMember;
	private String loanAmount;
	private String dueTime;
	private String rate;
	private String loanMode;
	private String warrantor;
	private String guarantyInfo;
	private String loanBeginTime;
	private String loanEndTime;
	private String payMode;
	private String loanAccount;
	private String remarks;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAge() {
		return customerAge;
	}
	public void setCustomerAge(String customerAge) {
		this.customerAge = customerAge;
	}
	public String getCustomerTele() {
		return customerTele;
	}
	public void setCustomerTele(String customerTele) {
		this.customerTele = customerTele;
	}
	public String getCustomerMarriage() {
		return customerMarriage;
	}
	public void setCustomerMarriage(String customerMarriage) {
		this.customerMarriage = customerMarriage;
	}
	public String getMateName() {
		return mateName;
	}
	public void setMateName(String mateName) {
		this.mateName = mateName;
	}
	public String getMateIdNo() {
		return mateIdNo;
	}
	public void setMateIdNo(String mateIdNo) {
		this.mateIdNo = mateIdNo;
	}
	public String getMateTele() {
		return mateTele;
	}
	public void setMateTele(String mateTele) {
		this.mateTele = mateTele;
	}
	public String getHomeAdd() {
		return homeAdd;
	}
	public void setHomeAdd(String homeAdd) {
		this.homeAdd = homeAdd;
	}
	public String getBusIndustry() {
		return busIndustry;
	}
	public void setBusIndustry(String busIndustry) {
		this.busIndustry = busIndustry;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getBusAdd() {
		return busAdd;
	}
	public void setBusAdd(String busAdd) {
		this.busAdd = busAdd;
	}
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	public String getChiefManager() {
		return chiefManager;
	}
	public void setChiefManager(String chiefManager) {
		this.chiefManager = chiefManager;
	}
	public String getSupManager() {
		return supManager;
	}
	public void setSupManager(String supManager) {
		this.supManager = supManager;
	}
	public String getApprovalMember() {
		return approvalMember;
	}
	public void setApprovalMember(String approvalMember) {
		this.approvalMember = approvalMember;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getLoanMode() {
		return loanMode;
	}
	public void setLoanMode(String loanMode) {
		this.loanMode = loanMode;
	}
	public String getWarrantor() {
		return warrantor;
	}
	public void setWarrantor(String warrantor) {
		this.warrantor = warrantor;
	}
	public String getGuarantyInfo() {
		return guarantyInfo;
	}
	public void setGuarantyInfo(String guarantyInfo) {
		this.guarantyInfo = guarantyInfo;
	}
	public String getLoanBeginTime() {
		return loanBeginTime;
	}
	public void setLoanBeginTime(String loanBeginTime) {
		this.loanBeginTime = loanBeginTime;
	}
	public String getLoanEndTime() {
		return loanEndTime;
	}
	public void setLoanEdnTime(String loanEndTime) {
		this.loanEndTime = loanEndTime;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getLoanAccount() {
		return loanAccount;
	}
	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
