var validator = $($formName).validate({
	rules : {
		customerName : {
			required : true
		},
		prdName : {
			required : true
		},
		chiefManager : {
			required : true
		},
		loanAmount : {
			required : true,
			number:true,min:1
		},
		dueTime : {
			required : true,
			number:true
		},
		rate : {
			required : true,
			number:true,max:99,min:1
		},
		loanMode : {
			required : true
		},
		loanBeginTime : {
			required : true
		},
		loanEndTime : {
			required : true
		}
	},
	messages : {
		customerName : {
			required : "<br>借款人姓名不能为空"
		},
		prdName : {
			required : "<br>贷款产品不能为空"
		},
		chiefManager : {
			required : "<br>主办客户经理不能为空"
		},
		loanAmount : {
			required : "<br>贷款金额不能为空",
			number:"<br>贷款金额只能为数字",min:"<br>贷款金额必须不能小于1"
		},
		dueTime : {
			required : "<br>贷款期限（月）不能为空",
			number:"<br>贷款期限（月）只能为数字"
		},
		rate : {
			required : "<br>利率(年)不能为空",
			number:"<br>利率(年)只能为数字",max:"<br>利率(年)不能大于99",min:"<br>利率(年)不能小于1"
		},
		loanMode : {
			required : "<br>信用/担保方式不能为空"
		},
		loanBeginTime : {
			required : "<br>放款日期不能为空"
		},
		loanEndTime : {
			required : "<br>到期日期不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});