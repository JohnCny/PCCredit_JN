package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.Iterator;
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

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.cardpay.pccredit.jnpad.service.JnpadIntopiecesDecisionService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadIntopiecesDecisionController extends BaseController{
	
	@Autowired
	private JnpadIntopiecesDecisionService jnpadIntopiecesDecisionService;
	@Autowired
	private IntoPiecesService intoPiecesService;
	@Autowired
	private ProductService productService;
	//进件初审
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/csBrowse.json", method = { RequestMethod.GET })
	public String csBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		
		
		filter.setNextNodeName("进件初审");
		filter.setUserId(request.getParameter("userId"));
		QueryResult<CustomerApplicationIntopieceWaitForm> result = jnpadIntopiecesDecisionService.findCustomerApplicationIntopieceDecison(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
		
	}
	//显示初审信息
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/csInfo.json", method = { RequestMethod.GET })
	public String csInfo(HttpServletRequest request){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
//		StringBuffer s=new StringBuffer();
//		Iterator<ManagerInfoForm> it = result.iterator(); 
//        while(it.hasNext()){  
//        ManagerInfoForm manager = it.next();
//        s.append("<option value = '"+manager.getID()+"'>"+manager.getEXTERNAL_ID()+manager.getDISPLAY_NAME()+"</option>"); 
//        } 
//        String ss = s.toString();
//        int size = result.size();  
        String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
		map.put("customerInfor",customerInfor);
//		map.put("managerInfo", result);
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
	}
	
	//下拉框选择客户经理信息
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/managerInfoi.json", method = { RequestMethod.GET })
	public String managerInfo(HttpServletRequest request){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
		int size = result.size();
		map.put("managerInfo", result);
		map.put("size", size);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 提交信息
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/updateAll.json")
	@JRadOperation(JRadOperation.APPROVE)
	public String update(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		if (returnMap.isSuccess()) {
			try {
				jnpadIntopiecesDecisionService.updateCustomerApplicationProcessBySerialNumber(request);
				returnMap.put("message","提交成功");
			} catch (Exception e) {
				returnMap.put("message","提交失败");
			}
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
	
	//审贷决议
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/sdBrowse.json", method = { RequestMethod.GET })
	public String sdbrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		String userId = request.getParameter("userId");
		filter.setNextNodeName("审贷决议");
		filter.setUserId(userId);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = jnpadIntopiecesDecisionService.findCustomerApplicationIntopieceDecison(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	//显示审议决议
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/sdjy.json", method = { RequestMethod.GET })
	public String input_decision(HttpServletRequest request) {
		
//		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
//		StringBuffer s=new StringBuffer();
//		Iterator<ManagerInfoForm> it = result.iterator(); 
//        while(it.hasNext()){  
//        ManagerInfoForm manager = it.next();
//        s.append("<option value = '"+manager.getID()+"'>"+manager.getEXTERNAL_ID()+manager.getDISPLAY_NAME()+"</option>"); 
//        } 
//        String ss = s.toString();
//		int size = result.size();
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		AppManagerAuditLog appManagerAuditLog = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId);
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
		map.put("customerApplicationProcess", processForm);
		map.put("appManagerAuditLog", appManagerAuditLog);
		map.put("custManagerId", customerInfor.getUserId());
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
//	//提交审贷决议
//	@ResponseBody
//	@RequestMapping(value = "update.json", method = { RequestMethod.GET })
//	public JRadReturnMap update(@ModelAttribute CustomerApplicationInfo customerApplicationInfo,HttpServletRequest request) {
//		JRadReturnMap returnMap = new JRadReturnMap();
//		try {
//			customerApplicationInfo.setActualQuote(customerApplicationInfo.getDecisionAmount());
//			jnpadIntopiecesDecisionService.update(customerApplicationInfo,request);
//			returnMap.addGlobalMessage(CHANGE_SUCCESS);
//		} catch (Exception e) {
//			return WebRequestHelper.processException(e);
//		}
//
//		return returnMap;
//	}
}
