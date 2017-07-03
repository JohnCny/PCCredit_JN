var validator = $($formName).validate({
	rules : {
		customerName : {
			required : true
		},
		applyTime : {
			required : true
		},
		applyAmount : {
			required : true,
			number:true,min:1
		},
		refuseTime : {
			required : true
		},
		refuseReason : {
			required : true
		}
	},
	messages : {
		customerName : {
			required : "<br>客户姓名不能为空"
		},
		applyTime : {
			required : "<br>申请日期不能为空"
		},
		applyAmount : {
			required : "<br>申请金额不能为空",
			number:"<br>申请金额只能为数字",min:"<br>申请金额必须不能小于1"
		},
		refuseTime : {
			required : "<br>拒绝日期不能为空"
		},
		refuseReason : {
			required : "<br>拒绝原因不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});