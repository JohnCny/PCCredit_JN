package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/*
 * 审批记录（审批客户经理、辅调客户经理记录）
 */
@ModelParam(table = "T_APP_MANAGER_AUDIT_LOG")
public class AppManagerAuditLog extends BusinessModel {
	
	private static final long serialVersionUID = -8470111754965975277L;
	
	private String  applicationId;//进件id
	private String  auditType;     
	private String  userId_1;    
	private String  userId_2;      
	private String  userId_3;
	private String  examineAmount;
	private String  examineLv;
	
	
	public String getExamineAmount() {
		return examineAmount;
	}
	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}
	public String getExamineLv() {
		return examineLv;
	}
	public void setExamineLv(String examineLv) {
		this.examineLv = examineLv;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getUserId_1() {
		return userId_1;
	}
	public void setUserId_1(String userId_1) {
		this.userId_1 = userId_1;
	}
	public String getUserId_2() {
		return userId_2;
	}
	public void setUserId_2(String userId_2) {
		this.userId_2 = userId_2;
	}
	public String getUserId_3() {
		return userId_3;
	}
	public void setUserId_3(String userId_3) {
		this.userId_3 = userId_3;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}