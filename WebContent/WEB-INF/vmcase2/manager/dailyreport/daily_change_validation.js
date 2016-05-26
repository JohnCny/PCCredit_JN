var validator = $($formName).validate({
	rules : {
		morningPlan : {
			required : true
		}
	},
	messages : {
		morningPlan : {
			required : "上午计划不能为空"
		}
	},
	errorPlacement : function(error, element) {
		element.after(error);
		if (layout) {
			layout.resizeLayout();
		}
	}
});