package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.JnpadMaintenanceFilter;
import com.cardpay.pccredit.jnpad.service.JnpadMaintenanceService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 维护日志
 * @author sealy
 *
 */
@Controller
public class JnpadMaintenanceController extends BaseController{
	@Autowired
	private JnpadMaintenanceService jnpadMaintenanceService;
	
	
	/**
	 * 查询客户经理
	 * @param request
	 * @param filter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectSubListManagerByManagerId.json", method = { RequestMethod.GET })
	public String selectSubListManagerByManagerId(HttpServletRequest request){
		
		String id =RequestHelper.getStringValue(request, "id");
		int userType = RequestHelper.getIntValue(request, "userType");
		
		List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(forms, jsonConfig);
		return json.toString();	

	}
	
	
	/**
	 * 获取指定客户经理的客户列表/客户维护日志
	 * @param filter
	 * @param request
	 * @return
	 */
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/product/getMaintenance.json", method = { RequestMethod.GET })
	public String getMaintenance( @ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
		String id =RequestHelper.getStringValue(request, "userId");
		int userType = RequestHelper.getIntValue(request, "userType");
		//查询下属客户经理
		List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
		if(forms.size()>0){
			filter.setCustomerManagerIds(forms);		
		}else{
			filter.setCustomerManagerIds(null);		
		}

		QueryResult<MaintenanceLog> result = jnpadMaintenanceService.findCustomerByFilter(filter);
//		JRadPagedQueryResult<MaintenanceLog> pagedResult = new JRadPagedQueryResult<MaintenanceLog>(filter, result);
//		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_log", request);
//		mv.addObject(PAGED_RESULT, pagedResult);
//		mv.addObject("forms", forms);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	
	}
		/**
		 * 获取维护计划列表
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/product/getMaintenanceList.json", method = { RequestMethod.GET })
		public String getMaintenanceList(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {

			String id =RequestHelper.getStringValue(request, "id");
			int userType = RequestHelper.getIntValue(request, "userType");
			filter.setCustomerManagerId(RequestHelper.getStringValue(request, "id"));
			List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
			String customerManagerId = filter.getCustomerManagerId();
			QueryResult<MaintenanceForm> result = null;
			if(customerManagerId!=null && !customerManagerId.equals("")){
				result = jnpadMaintenanceService.findMaintenancePlansList(filter);
			}else{
				if(forms.size()>0){
					filter.setCustomerManagerIds(forms);
					result = jnpadMaintenanceService.findMaintenancePlansList(filter);
				}else{
					
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(result, jsonConfig);
					return json.toString();

				}
			}
			JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(pagedResult, jsonConfig);
			return json.toString();
		}

}
