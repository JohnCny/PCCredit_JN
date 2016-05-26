package com.cardpay.pccredit.jnpad.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CIPERSONBASINFO;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerSelectService;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 客户信息查询
 * @author sealy
 *
 */
@Controller	
public class JnpadCustomerSelectController {

	@Autowired
	private JnpadCustomerSelectService customerSelectSercice;
	/**
	 * 根据证件号码查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectCustomerInfoByCardId.json", method = { RequestMethod.GET })
public String selectCustomerInfoByCardId(HttpServletRequest request){
		
	String cardId = RequestHelper.getStringValue(request, "cardId");
	CustomerInfo customer = customerSelectSercice.selectCustomerInfoByCardId(cardId);
	
	
	
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	JSONObject json = JSONObject.fromObject(customer, jsonConfig);
	
	
	return json.toString();
	}
	
	
	/**
	 * 
	 * 按id查找相应的客户基本信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectCustomerById.json", method = { RequestMethod.GET })
	public String selectCustomerFirsthendById(HttpServletRequest request){

		String id = RequestHelper.getStringValue(request, "id");
		CIPERSONBASINFO customer = customerSelectSercice.selectCustomerInfoById(id);
		
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(customer, jsonConfig);
		
		
		return json.toString();
	}
	
	
	/**
	 * 
	 * 按客户内码id查找相应的客户基本信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectCustomerByNm.json", method = { RequestMethod.GET })
	public String findCustomerFirsthendByNm(HttpServletRequest request){
		
		String custid = RequestHelper.getStringValue(request, "custid");
		CIPERSONBASINFO customer = customerSelectSercice.selectCustomerByNm(custid);
		
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(customer, jsonConfig);
		
		
		return json.toString();
		
		
		
	}
}

