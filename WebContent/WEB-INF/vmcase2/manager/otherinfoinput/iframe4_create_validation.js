var validator = $($formName).validate({
	rules : {
		visitId : {
			required : true
		},
		customerName :{
			required : true
		}
	},
	messages : {
		visitId : {
			required : "拜访客户经理不能为空"
		},
		customerName :{
			required : "客户姓名不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});