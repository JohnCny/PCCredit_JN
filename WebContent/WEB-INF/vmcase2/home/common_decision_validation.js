var validator = $($formName).validate({
	rules : {
		applyAmount : {
			required : true
		},
		loanUse :{
			required : true
		},
		sex :{
			required : true
		},
		age :{
			required : true,number:true
		},
		education :{
			required : true
		},
		residence :{
			required : true
		},
		marriage :{
			required : true
		},
		childrenEducation :{
			required : true
		},
		ownedPropertyQuantity :{
			required : true,number:true
		},
		mortgagePropertyQuantity :{
			required : true,number:true
		},
		mortgateBalance :{
			required : true
		},
		ownedCarsQuantity :{
			required : true,number:true
		},
		businessYears :{
			required : true,number:true
		},
		credit :{
			required : true
		},
		creditCardOverdueCount :{
			required : true,number:true
		},
		loanOverdueCount :{
			required : true,number:true
		},
		loanBalance :{
			required : true
		},
		mortgageRemaining :{
			required : true
		},
		numOfEconomicDependence :{
			required : true,number:true
		},
		liquidAssents :{
			required : true
		},
		stock :{
			required : true
		},
		fixedAssents :{
			required : true
		},
		shortTermLiabilities :{
			required : true
		},
		totalLiabilities :{
			required : true
		},
		totalAssents :{
			required : true
		},
		ownersEquity :{
			required : true
		},
		annualIncome :{
			required : true
		},
		otherIncome :{
			required : true
		},
		spouseIncome :{
			required : true
		},
		paymentByPrivateUse :{
			required : true
		},
		annualDisposableCapital :{
			required : true
		},
		articleCategory :{
			required : true
		},
		collateralValuation :{
			required : true
		},
		cname :{
			required : true
		},
		cardNo :{
			required : true
		}
	},
	messages : {
		applyAmount : {
			required : "申请贷款金额不能为空"
		},
		loanUse :{
			required : "贷款用途不能为空"
		},
		sex :{
			required : "性别不能为空"
		},
		age :{
			required : "年龄不能为空",number : "请输入整数"
		},
		education :{
			required : "最高学历学位不能为空"
		},
		residence :{
			required : "户籍所在地不能为空"
		},
		marriage :{
			required : "婚姻状况不能为空"
		},
		childrenEducation :{
			required : "子女教育情况不能为空"
		},
		ownedPropertyQuantity :{
			required : "自有房产数量不能为空",number : "请输入整数"
		},
		mortgagePropertyQuantity :{
			required : "按揭房产数量不能为空",number : "请输入整数"
		},
		mortgateBalance :{
			required : "按揭贷款余额不能为空"
		},
		ownedCarsQuantity :{
			required : "自有车辆数量不能为空",number : "请输入整数"
		},
		businessYears :{
			required : "业务年限不能为空",number : "请输入整数"
		},
		credit :{
			required : "信用状况不能为空"
		},
		creditCardOverdueCount :{
			required : "信用逾期次数不能为空",number : "请输入整数"
		},
		loanOverdueCount :{
			required : "贷款逾期次数不能为空",number : "请输入整数"
		},
		loanBalance :{
			required : "贷款余额不能为空"
		},
		mortgageRemaining :{
			required : "担保余额不能为空"
		},
		numOfEconomicDependence :{
			required : "经济上依赖的人数不能为空",number : "请输入整数"
		},
		liquidAssents :{
			required : "流动资产不能为空"
		},
		stock :{
			required : "存货不能为空"
		},
		fixedAssents :{
			required : "固定资产不能为空"
		},
		shortTermLiabilities :{
			required : "短期负债不能为空"
		},
		totalLiabilities :{
			required : "负债总计不能为空"
		},
		totalAssents :{
			required : "资产总计不能为空"
		},
		ownersEquity :{
			required : "所有者权益不能为空"
		},
		annualIncome :{
			required : "主营业务收入不能为空"
		},
		otherIncome :{
			required : "其他工作年收入不能为空"
		},
		spouseIncome :{
			required : "配偶年收入不能为空"
		},
		paymentByPrivateUse :{
			required : "私人用途分期付款不能为空"
		},
		annualDisposableCapital :{
			required : "年可支配收入不能为空"
		},
		articleCategory :{
			required : "抵质押物品种类不能为空"
		},
		collateralValuation :{
			required : "抵押物估值不能为空"
		},
		cname :{
			required : "姓名不能为空"
		},
		cardNo :{
			required : "身份证号不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});
