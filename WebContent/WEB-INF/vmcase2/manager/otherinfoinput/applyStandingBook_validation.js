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
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});