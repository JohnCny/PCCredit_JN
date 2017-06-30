package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 拒绝台账
 */
@ModelParam(table = "LOAN_REFUSED")
public class LoanRefused extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String customerName;
	private String busAdd;
	private String applyTime;
	private String applyAmount;
	private String refuseTime;
	private String refuseReason;
	private String chiefManager;
	private String supManager;
	private String loanMode;
	private String remarks;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBusAdd() {
		return busAdd;
	}
	public void setBusAdd(String busAdd) {
		this.busAdd = busAdd;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getRefuseTime() {
		return refuseTime;
	}
	public void setRefuseTime(String refuseTime) {
		this.refuseTime = refuseTime;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
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
	public String getLoanMode() {
		return loanMode;
	}
	public void setLoanMode(String loanMode) {
		this.loanMode = loanMode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
