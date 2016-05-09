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

import com.cardpay.pccredit.ipad.service.CustomerInforForIpadService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.cardpay.pccredit.jnpad.service.JnIpadUserLoginService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

/**
 * ipad interface
 * 3.1.2 客户进件信息
 * songchen
 * 2016年05月09日   下午1:54:18
 */
@Controller
public class JnIpadCustAppInfoXxController {
	
	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;

	
	/**
	 * 进件信息查询 
	 * 【查询进件数量/拒绝进件数量/申请通过进件数量/补充调查进件数量】
	 * null|refuse|approved|null
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/prodBrowse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		//当前登录用户id
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId =user.getLastName();
		
		//获取请求参数
		//null
		String status1=request.getParameter("status1");
		//refuse
		String status2=request.getParameter("status2");
		//approved
		String status3=request.getParameter("status3");
		//nopass_replenish
		String status4=request.getParameter("status4");
		
		int i = appInfoXxService.findCustomerApplicationInfoCount(userId,
																  status1,
																  status2,
																  status3,
																  status4);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", i);//统计数量
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	
	
}
