var validator = $($formName).validate({
	rules : {
		sex : {
			required : true
		},
		houseAddress : {
			required : true
		},
		carVersion : {
			required : true
		},
		familyNum : {
			required : true
		},
		contactName : {
			required : true
		},
		beginDateString : {
			required : true
		},
		companyName : {
			required : true
		},
		businessLine : {
			required : true
		},
		openBank : {
			required : true
		},
		accuont : {
			required : true
		},
		monetaryDateString : {
			required : true
		}
	},
	messages : {
		sex : {
			required : "申请人性别不能为空"
		},
		houseAddress : {
			required : "房产地址不能为空"
		},
		carVersion : {
			required : "汽车车型不能为空"
		},
		familyNum : {
			required : "家庭成员不能为空"
		},
		contactName : {
			required : "联系人姓名不能为空"
		},
		beginDateString : {
			required : "日期不能为空"
		},
		companyName : {
			required : "企业名称不能为空"
		},
		businessLine : {
			required : "主要业务范围不能为空"
		},
		openBank : {
			required : "开户行不能为空"
		},
		accuont : {
			required : "账号不能为空"
		},
		monetaryDateString : {
			required : "购买日期不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});