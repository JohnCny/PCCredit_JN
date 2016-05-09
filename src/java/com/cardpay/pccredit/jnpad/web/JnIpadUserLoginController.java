package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.service.CustomerInforForIpadService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.JnUserLoginIpad;
import com.cardpay.pccredit.jnpad.model.JnUserLoginResult;
import com.cardpay.pccredit.jnpad.service.JnIpadUserLoginService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.util.web.RequestHelper;

/**
 * ipad interface
 * pad用户登录
 * songchen
 * 2016年04月16日   下午1:54:18
 */
@Controller
public class JnIpadUserLoginController {
	
	@Autowired
	private JnIpadUserLoginService userService;
	
	@Autowired
	private CustomerInforForIpadService customerInforService;
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/JnLogin.json")
	@JRadOperation(JRadOperation.BROWSE)
	public String login(HttpServletRequest request) {
		String login = RequestHelper.getStringValue(request, "login");
		String passwd = RequestHelper.getStringValue(request, "password");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Result result = null;
		JnUserLoginResult loginResult = null;
		if(StringUtils.isEmpty(login) || StringUtils.isEmpty(passwd)){
			result = new Result();
			result.setStatus(IpadConstant.FAIL);
			result.setReason(IpadConstant.LOGINNOTNULL);
			map.put("result",result);
		}else{
			loginResult = new JnUserLoginResult();
			JnUserLoginIpad user = userService.login(login, passwd);
			if(user!=null){
				loginResult.setUser(user);
				loginResult.setStatus(IpadConstant.SUCCESS);
				loginResult.setReason(IpadConstant.LOGINSUCCESS);
			}else{
				loginResult.setStatus(IpadConstant.FAIL);
				loginResult.setReason(IpadConstant.LOGINFAIL);
			}
			map.put("result",loginResult);
		}
		JSONObject json = JSONObject.fromObject(map);
		return String.valueOf(json);
	}
	
	
	/**
	 * 产品查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/prodBrowse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		int page = 1;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		List<ProductAttribute> products = userService.findProducts(page,limit);
		int totalCount = userService.findProductsCount();
		result.put("totalCount", totalCount);
		result.put("result", products);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 客户新增
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/customerInsert.json")
	@JRadOperation(JRadOperation.CREATE)
	public String insert(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String name = request.getParameter("name");
		String cardId = request.getParameter("cardId");
		String cardType = request.getParameter("cardType");
		
		String userId = request.getParameter("userId");
		map = customerInforService.addCustomer(name,cardId,cardType,userId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	
	/**
	 * 客户进件信息
	 * 查询进件数量及审核通过的进件量
	 */
	/*@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/customerInsert.json")
	@JRadOperation(JRadOperation.CREATE)
	public String findCustomerAppIntoCount(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		
		String userId = request.getParameter("userId");
		
		List<CustomerAppInfoIpad> list = null;
		list = 
		map.put("result", list);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	*/
	
}
