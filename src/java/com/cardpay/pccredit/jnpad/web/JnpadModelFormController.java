package com.cardpay.pccredit.jnpad.web;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.creditEvaluation.vo.CommonDecisionForm;
import com.cardpay.pccredit.intopieces.model.EvaResult;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadModelFormService;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.modules.log.model.OperationLog;
import com.wicresoft.jrad.modules.log.service.UserLogService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadModelFormController {
	
	@Autowired
	JnpadModelFormService jnpadmodelformservice;
	@Autowired
	private UserLogService userLogService;
	
	@ResponseBody
	@RequestMapping(value = "/ipad/modelForm/insertresult.json", method = { RequestMethod.GET })
	public String getModelResult(@ModelAttribute CommonDecisionForm commondecisionform,HttpServletRequest request){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			String msg = IdCardValidate.IDCardValidate(commondecisionform.getCardNo());
		
		if(msg !="" && msg != null){
			map.put("mess", msg);
			map.put("issuccess", false);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
		}
		} catch (ParseException e) {
			map.put("mess", "身份证验证出现未知错误");
			map.put("issuccess", false);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
		}
		EvaResult evaresult=jnpadmodelformservice.saveEvaModelResult(commondecisionform);
		OperationLog ol = new OperationLog();
		ol.setUser_id(commondecisionform.getUserId());
	    ol.setUser_login(commondecisionform.getUserName());
	    ol.setModule("PAD端模型评估");
	    ol.setOperation_result("SUCCESS");
	    ol.setOperation_name("ADD");
	    ol.setIp_address(request.getRemoteAddr());
		userLogService.addUserLog(ol);
		map.put("evaresult", evaresult);
		map.put("issuccess", true);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
		
	}
	@ResponseBody
	@RequestMapping(value = "/ipad/modelForm/selectresult.json", method = { RequestMethod.GET })
	public String selectModelResult(HttpServletRequest request){
		String userId=request.getParameter("userId");
		String userType=request.getParameter("userType");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(!userType.equals("1")){
			userId=null;
		}
		List<EvaResult> result=jnpadmodelformservice.getEvaModelResult(userId);
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
		
	}

}
