var validator = $($formName).validate({
	rules : {
		managerName : {
			required : true
		},
		customerName :{
			required : true
		},
		applyAmount :{
			required : true
		},
		applyDate :{
			required : true
		},
		applyRefuseReason :{
			required : true
		},
		signDate :{
			required : true
		},
		queryDate :{
			required : true
		},
		queryReason :{
			required : true
		},
		hostManager :{
			required : true
		},
		assistManager :{
			required : true
		},
		actualDate :{
			required : true
		},
		tabulaTime :{
			required : true
		},
		internalAuditDate :{
			required : true
		},
		internalAuditor :{
			required : true
		},
		internalAuditProdType :{
			required : true
		},
		internalAuditAmt :{
			required : true
		},
		appInterest :{
			required : true
		},
		appPeriod :{
			required : true
		},
		appRepayMethod :{
			required : true
		},
		oneMeetDate :{
			required : true
		},
		auditUser :{
			required : true
		},
		approvedMeetDate :{
			required : true
		},
		approvedMeetProdType :{
			required : true
		},
		approvedAmt :{
			required : true
		},
		approvedLv :{
			required : true
		},
		approvedPeriod :{
			required : true
		},
		approvedRepayMethod :{
			required : true
		},
		meetRefuseDate :{
			required : true
		},
		meetRefuseReason :{
			required : true
		},
		dateSign :{
			required : true
		},
		signPerson :{
			required : true
		},
		signPlace :{
			required : true
		},
		applyRefuseDate :{
			required : true
		},
		creditRefuseDate :{
			required : true
		},
		creditRefuseReason :{
			required : true
		}
	},
	messages : {
		managerName : {
			required : "客户经理不能为空"
		},
		customerName :{
			required : "客户姓名不能为空"
		},
		applyAmount :{
			required : "申请金额不能为空"
		},
		applyDate :{
			required : "申请日期不能为空"
		},
		applyRefuseReason :{
			required : "申请拒绝原因不能为空"
		},
		signDate :{
			required : "签字日期不能为空"
		},
		queryDate :{
			required : "查询日期不能为空"
		},
		queryReason :{
			required : "查询原因不能为空"
		},
		hostManager :{
			required : "主办客户经理不能为空"
		},
		assistManager :{
			required : "协办客户经理不能为空"
		},
		actualDate :{
			required : "实调日期不能为空"
		},
		tabulaTime :{
			required : "制表时间不能为空"
		},
		internalAuditDate :{
			required : "内审日期不能为空"
		},
		internalAuditor :{
			required : "内审人员不能为空"
		},
		internalAuditProdType :{
			required : "内审产品类型不能为空"
		},
		internalAuditAmt :{
			required : "内审金额不能为空"
		},
		appInterest :{
			required : "申请利率不能为空"
		},
		appPeriod :{
			required : "申请期限不能为空"
		},
		appRepayMethod :{
			required : "申请还款方式不能为空"
		},
		oneMeetDate :{
			required : "一次上会日期不能为空"
		},
		auditUser :{
			required : "审贷人员不能为空"
		},
		approvedMeetDate :{
			required : "过会日期不能为空"
		},
		approvedMeetProdType :{
			required : "过会产品类型不能为空"
		},
		approvedAmt :{
			required : "过会金额不能为空"
		},
		approvedLv :{
			required : "过会利率不能为空"
		},
		approvedPeriod :{
			required : "过会期限不能为空"
		},
		approvedRepayMethod :{
			required : "过会还款方式不能为空"
		},
		meetRefuseDate :{
			required : "上会拒绝日期不能为空"
		},
		meetRefuseReason :{
			required : "拒绝原因不能为空"
		},
		dateSign :{
			required : "签约日期不能为空"
		},
		signPerson :{
			required : "签约人员不能为空"
		},
		signPlace :{
			required : "签约地点不能为空"
		},
		applyRefuseDate :{
			required : "申请拒绝日期不能为空"
		},
		creditRefuseDate :{
			required : "征信拒绝日期不能为空"
		},
		creditRefuseReason :{
			required : "征信拒绝原因不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});