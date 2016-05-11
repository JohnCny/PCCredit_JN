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

import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.CustomerApprovedFilter;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

/**
 * ipad interface
 * 3.1.2 客户进件信息
 * 3.1.3 客户运营状况
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
	@RequestMapping(value = "/ipad/custAppInfo/browse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		//获取当前登录用户id
		String userId=request.getParameter("userId");
		
		//获取请求参数
		//null
		String status1=request.getParameter("status1");
		//refuse
		String status2=request.getParameter("status2");
		//approved
		String status3=request.getParameter("status3");
		//nopass_replenish
		String status4=request.getParameter("status4");
		int i = appInfoXxService.findCustAppInfoXxCount(userId,status1,status2, status3,status4);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", i);//统计数量
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 查询审核通过的进件
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findintoApprovedPieces.json", method = { RequestMethod.GET })
	public String findintoApprovedPieces(HttpServletRequest request,CustomerApprovedFilter filter) {
		//获取当前登录用户id
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		//获取分页参数
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		
		int page = 0;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		filter.setPage(page);
		filter.setLimit(limit);
		
		//审核通过
		filter.setStatus("approved");
		List<IntoPieces> list = appInfoXxService.findCustomerApprovedList(filter);
		int totalCount = appInfoXxService.findCustomerApprovedCountList(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 根据条件查询进件
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findAppintoPiecesByParams.json", method = { RequestMethod.GET })
	public String findAppintoPiecesByParams(HttpServletRequest request,CustomerApprovedFilter filter) {
		//获取当前登录用户id
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		//分页参数
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		
		int page = 0;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		
		filter.setPage(page);
		filter.setLimit(limit);
		List<IntoPieces> list = appInfoXxService.findCustomerApprovedList(filter);
		int totalCount = appInfoXxService.findCustomerApprovedCountList(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 更新进件状态
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/updateCustomerApplicationInfo.json")
	@JRadOperation(JRadOperation.CREATE)
	public String updateCustomerApplicationInfo(HttpServletRequest request,CustomerApprovedFilter filter) {
		//请求参数
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		
		filter.setId(id);
		filter.setStatus(status);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map = appInfoXxService.updateCustomerApplicationInfo(filter);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 统计已授信额度
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/calCreditAmt.json", method = { RequestMethod.GET })
	public String calCreditAmt(HttpServletRequest request,CustomerApprovedFilter filter) {
		//当前登录用户id
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		
		String amt = appInfoXxService.calCreditAmt(filter);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		
		//统计数量
		result.put("amt", amt);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 逾期客户数
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/overdueCustomerCount.json", method = { RequestMethod.GET })
	public String overdueCustomerCount(HttpServletRequest request,CustomerApprovedFilter filter) {
		//获取当前登录用户id
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		int num =  appInfoXxService.overdueCustomerCount(filter);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", num);//逾期客户数
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 核销客户数
	 * ???暂时没记录
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/cavCustomerCount.json", method = { RequestMethod.GET })
	public String cavCustomerCount(HttpServletRequest request,CustomerApprovedFilter filter) {
		//获取当前登录用户id
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		
		//TODO
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", 0);//核销客户数
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
}
