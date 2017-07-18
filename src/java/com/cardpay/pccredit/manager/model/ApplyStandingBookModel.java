package com.cardpay.pccredit.manager.model;

import java.util.Date;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 申请统计台账
 */
@ModelParam(table = "ApplyStandingBook")
public class ApplyStandingBookModel extends BaseModel{
	
	
	private static final long serialVersionUID = 1L;
	private String managerId; 
	private Date applyDate; //申请日期
	private String customerName;//客户姓名
	private String contactMethod;//联系方式
	private String homeAddress;//家庭住址
	private String workPlace;//营业实体/工作单位
	private String product;//贷款产品
	private String applyAmount;//申请金额
	private String applyDeadline;//申请期限
	private String proceedUse;//贷款用途
	private String financingExperience;//是否有融资经历（贷款信息）
	private String managerName;//接待客户经理
	private String jurisdiction;//所属管辖行
	private String remark;//备注
	private Date createdTime;
	
	private String vistedId;//拜访id
	
	private String state;//审批状态
	
	/*申请拒绝数*/
	private String applyRefuseReason;//申请拒绝原因
	private String applyRefuseDate;//申请拒绝日期
	
	/*征信数*/
	private String queryReason;  //查询原因
	private String queryDate;    //查询日期
	private String signDate;     //客户签字日期
	
	/*征信拒绝数*/
	private String creditRefuseReason;//征信拒绝原因
	private String creditRefuseDate;//征信拒绝日期
	
	/*实调数*/
	private String  hostManager;//主办客户经理
	private String  hostId;//主办客户经理id
	private String  assistManager;//协办客户经理
	private String  assistId;//协办客户经理id
	private String  actualDate;//实调日期
	
	/*报告数*/
	private String  tabulaTime; //制表时间
	

	/*内审数*/
    private String internalAuditDate;//内审日期
    private String internalAuditor;//内审人员
    private String internalAuditProdType;//内审产品类型
    private String internalAuditAmt;//内审产品金额
    private String appInterest;//申请利率
    private String appPeriod;//申请期限
    private String appRepayMethod;//申请还款方式
    
    /*上会数*/
    private String oneMeetDate;//一次上会日期
    private String twoMeetDate;//二次上会日期
	private String threeMeetDate;//二次上会日期
    private String auditUser;//审贷人员
    

    /*上会通过数*/
    private String approvedMeetDate;//过会日期
    private String approvedMeetProdType;//过会产品类型
    private String approvedAmt;//过会金额
    private String approvedLv;//过会利率
    private String approvedPeriod;//过会期限
    private String approvedRepayMethod;//过会还款方式
    
    /*上会拒绝数*/
    private String meetRefuseDate;//上会拒绝日期
    private String meetRefuseReason;//拒绝原因
    
    /*审核审批*/
    private String remark1;//小微贷负责人备注
    private String remark2;//零售业务部负责人备注
    private String remark3;//行长备注
    
    /*签约数*/
    private String dateSign;//签字日期
    private String signPerson;//签字人员
    private String signPlace;//签字地点
    
    
    
	public String getCreditRefuseDate() {
		return creditRefuseDate;
	}
	public void setCreditRefuseDate(String creditRefuseDate) {
		this.creditRefuseDate = creditRefuseDate;
	}
	public String getApplyRefuseDate() {
		return applyRefuseDate;
	}
	public void setApplyRefuseDate(String applyRefuseDate) {
		this.applyRefuseDate = applyRefuseDate;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactMethod() {
		return contactMethod;
	}
	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getApplyDeadline() {
		return applyDeadline;
	}
	public void setApplyDeadline(String applyDeadline) {
		this.applyDeadline = applyDeadline;
	}
	public String getProceedUse() {
		return proceedUse;
	}
	public void setProceedUse(String proceedUse) {
		this.proceedUse = proceedUse;
	}
	public String getFinancingExperience() {
		return financingExperience;
	}
	public void setFinancingExperience(String financingExperience) {
		this.financingExperience = financingExperience;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getVistedId() {
		return vistedId;
	}
	public void setVistedId(String vistedId) {
		this.vistedId = vistedId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getApplyRefuseReason() {
		return applyRefuseReason;
	}
	public void setApplyRefuseReason(String applyRefuseReason) {
		this.applyRefuseReason = applyRefuseReason;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getCreditRefuseReason() {
		return creditRefuseReason;
	}
	public void setCreditRefuseReason(String creditRefuseReason) {
		this.creditRefuseReason = creditRefuseReason;
	}
	public String getHostManager() {
		return hostManager;
	}
	public void setHostManager(String hostManager) {
		this.hostManager = hostManager;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getAssistManager() {
		return assistManager;
	}
	public void setAssistManager(String assistManager) {
		this.assistManager = assistManager;
	}
	public String getAssistId() {
		return assistId;
	}
	public void setAssistId(String assistId) {
		this.assistId = assistId;
	}
	public String getActualDate() {
		return actualDate;
	}
	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}
	public String getTabulaTime() {
		return tabulaTime;
	}
	public void setTabulaTime(String tabulaTime) {
		this.tabulaTime = tabulaTime;
	}
	public String getInternalAuditDate() {
		return internalAuditDate;
	}
	public void setInternalAuditDate(String internalAuditDate) {
		this.internalAuditDate = internalAuditDate;
	}
	public String getInternalAuditor() {
		return internalAuditor;
	}
	public void setInternalAuditor(String internalAuditor) {
		this.internalAuditor = internalAuditor;
	}
	public String getInternalAuditProdType() {
		return internalAuditProdType;
	}
	public void setInternalAuditProdType(String internalAuditProdType) {
		this.internalAuditProdType = internalAuditProdType;
	}
	public String getInternalAuditAmt() {
		return internalAuditAmt;
	}
	public void setInternalAuditAmt(String internalAuditAmt) {
		this.internalAuditAmt = internalAuditAmt;
	}
	public String getAppInterest() {
		return appInterest;
	}
	public void setAppInterest(String appInterest) {
		this.appInterest = appInterest;
	}
	public String getAppPeriod() {
		return appPeriod;
	}
	public void setAppPeriod(String appPeriod) {
		this.appPeriod = appPeriod;
	}
	public String getAppRepayMethod() {
		return appRepayMethod;
	}
	public void setAppRepayMethod(String appRepayMethod) {
		this.appRepayMethod = appRepayMethod;
	}
	public String getOneMeetDate() {
		return oneMeetDate;
	}
	public void setOneMeetDate(String oneMeetDate) {
		this.oneMeetDate = oneMeetDate;
	}
	public String getTwoMeetDate() {
		return twoMeetDate;
	}
	public void setTwoMeetDate(String twoMeetDate) {
		this.twoMeetDate = twoMeetDate;
	}
	public String getThreeMeetDate() {
		return threeMeetDate;
	}
	public void setThreeMeetDate(String threeMeetDate) {
		this.threeMeetDate = threeMeetDate;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getApprovedMeetDate() {
		return approvedMeetDate;
	}
	public void setApprovedMeetDate(String approvedMeetDate) {
		this.approvedMeetDate = approvedMeetDate;
	}
	public String getApprovedMeetProdType() {
		return approvedMeetProdType;
	}
	public void setApprovedMeetProdType(String approvedMeetProdType) {
		this.approvedMeetProdType = approvedMeetProdType;
	}
	public String getApprovedAmt() {
		return approvedAmt;
	}
	public void setApprovedAmt(String approvedAmt) {
		this.approvedAmt = approvedAmt;
	}
	public String getApprovedLv() {
		return approvedLv;
	}
	public void setApprovedLv(String approvedLv) {
		this.approvedLv = approvedLv;
	}
	public String getApprovedPeriod() {
		return approvedPeriod;
	}
	public void setApprovedPeriod(String approvedPeriod) {
		this.approvedPeriod = approvedPeriod;
	}
	public String getApprovedRepayMethod() {
		return approvedRepayMethod;
	}
	public void setApprovedRepayMethod(String approvedRepayMethod) {
		this.approvedRepayMethod = approvedRepayMethod;
	}
	public String getMeetRefuseDate() {
		return meetRefuseDate;
	}
	public void setMeetRefuseDate(String meetRefuseDate) {
		this.meetRefuseDate = meetRefuseDate;
	}
	public String getMeetRefuseReason() {
		return meetRefuseReason;
	}
	public void setMeetRefuseReason(String meetRefuseReason) {
		this.meetRefuseReason = meetRefuseReason;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getDateSign() {
		return dateSign;
	}
	public void setDateSign(String dateSign) {
		this.dateSign = dateSign;
	}
	public String getSignPerson() {
		return signPerson;
	}
	public void setSignPerson(String signPerson) {
		this.signPerson = signPerson;
	}
	public String getSignPlace() {
		return signPlace;
	}
	public void setSignPlace(String signPlace) {
		this.signPlace = signPlace;
	}
}
