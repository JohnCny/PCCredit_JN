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
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});


/** 申请贷款金额，单位：元
private double applyAmount;

* 贷款用途，0—消费（如旅游、购车、购住房、装修住房、教育），1—经营
private int loanUse;

* 性别，0—男，1—女
private int sex;

* 年龄
private int age;

* 最高学位学历，0—初中及以下，1—高中及技校，2—大学及以上    
private int education;

* 户籍所在地，0—本地，1—本省外地，2—省外
private int residence;

* 婚姻状况，0—已婚，1—未婚，2—离婚，3—再婚
private int marriage;

* 子女教育状况，0—无子女，1—上学，2—学龄前，3—工作
private int childrenEducation;

* 自有房产数量
private int ownedPropertyQuantity;

* 按揭房产数量
private int mortgagePropertyQuantity;

* 按揭贷款余额，单位：元
private double mortgateBalance;

* 自有车辆数量
private int ownedCarsQuantity;

* 业务年限
private int businessYears;

* 信用状况-- 1—正常,2—不正常，3—无记录
private int credit;

* 信用逾期次数
private int creditCardOverdueCount;

* 贷款逾期次数
private int loanOverdueCount;

* 贷款余额，单位：元
private double loanBalance;

* 担保余额，单位：元
private double mortgageRemaining;

* 经济上依赖的人数
private int numOfEconomicDependence;

* 流动资产，单位：元
private Double liquidAssents;

* 存货，单位：元
private Double stock;

* 固定资产，单位：元
private Double fixedAssents;

* 短期负债，单位：元
private Double shortTermLiabilities;

* 负债总计，单位：元
private Double totalLiabilities;

* 资产总计，单位：元
private Double totalAssents;

* 所有者权益，单位：元
private double ownersEquity;

* 主营业务收入，单位：元
private double annualIncome;

* 其他工作年收入，单位：元
private double otherIncome;

* 配偶年收入，单位：元
private double spouseIncome;

* 私人用途分期付款，单位：元
private double paymentByPrivateUse;

* 年可支配收入，单位：元
private double annualDisposableCapital;

* 抵质押物品种类，0：商业房产,1：住宅
private int articleCategory;

* 抵押物估值，单位：元
private double collateralValuation;*/