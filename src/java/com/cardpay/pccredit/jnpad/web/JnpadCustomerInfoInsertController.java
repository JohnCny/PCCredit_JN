package com.cardpay.pccredit.jnpad.web;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerBank;
import com.cardpay.pccredit.jnpad.model.CustomerCarInfo;
import com.cardpay.pccredit.jnpad.model.CustomerCompanyBusiness;
import com.cardpay.pccredit.jnpad.model.CustomerCompanyInfo;
import com.cardpay.pccredit.jnpad.model.CustomerContact;
import com.cardpay.pccredit.jnpad.model.CustomerFamilyInfo;
import com.cardpay.pccredit.jnpad.model.CustomerHouse;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.CustomerInfoForm;
import com.cardpay.pccredit.jnpad.model.CustomerLiving;
import com.cardpay.pccredit.jnpad.model.CustomerPersonal;
import com.cardpay.pccredit.jnpad.model.CustomerStore;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerInfoInsertServ‎ice;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerSelectService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 
 * 新建客户
 * @author sealy
 *
 */
@Controller
public class JnpadCustomerInfoInsertController extends BaseController {
	
	@Autowired
	private JnpadCustomerInfoInsertServ‎ice JnpadCustomerInfoInsertServ‎ice;
	@Autowired
	private JnpadCustomerSelectService jnpadCustomerSelectService; 
	@Autowired
	private IntoPiecesService intoPiecesService;
	
	@ResponseBody
	@RequestMapping(value="/ipad/product/customerInsert.json" ,method = { RequestMethod.GET })
	public String customerInsert(@ModelAttribute CustomerInfoForm customerinfoForm ,HttpServletRequest request){
		
		customerinfoForm.setChineseName(request.getParameter("chineseName"));
		customerinfoForm.setCardType(request.getParameter("cardType"));
		customerinfoForm.setCardId(request.getParameter("cardId"));
		customerinfoForm.setSpmc(request.getParameter("spmc"));
		customerinfoForm.setTelephone(request.getParameter("phoneNumber"));
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				CustomerInforFilter filter = new CustomerInforFilter();
				filter.setCardId(customerinfoForm.getCardId());
				//身份证号码验证
				if(customerinfoForm.getCardType()=="0"||customerinfoForm.getCardType().equals("0")){
				String msg = IdCardValidate.IDCardValidate(customerinfoForm.getCardId());
				if(msg !="" && msg != null){
					returnMap.put(JRadConstants.MESSAGE, msg);
					returnMap.put(JRadConstants.SUCCESS, false);
					
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
					return json.toString();
				}
				}
				CustomerInfo ls = jnpadCustomerSelectService.selectCustomerInfoByCardId(filter.getCardId());
				if(ls != null ){
					String message = "";
					String gId = ls.getUserId();
					String managerName = jnpadCustomerSelectService.selectManagerNameById(gId);
					if(gId==null){
						message = "保存失败，此客户已存在，请线下及时联系!";
					}else{
						message = "保存失败，此客户已挂在客户经理【"+managerName+"】名下!";
					}
					returnMap.put(JRadConstants.MESSAGE, message);
					//查询是否为风险名单
//					List<RiskCustomer> riskCustomers = customerInforService.findRiskByCardId(customerinfoForm.getCardId());
//					if(riskCustomers.size()>0){
//						SystemUser user = customerInforService.getUseById(riskCustomers.get(0).getCreatedBy());
//						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						String dateString = format.format(riskCustomers.get(0).getCreatedTime());
//						message+="此客户于"+dateString+"被"+user.getDisplayName()+"拒绝，原因为"+riskCustomers.get(0).getRefuseReason();
//					}
//					returnMap.put(JRadConstants.MESSAGE, message);
//					returnMap.put(JRadConstants.SUCCESS, false);
//					
//					JsonConfig jsonConfig = new JsonConfig();
//					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
//					JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
//					return json.toString();				
					}else{
					returnMap.put(JRadConstants.SUCCESS, true);

				
				CustomerInfo customerinfor = customerinfoForm.createModel(CustomerInfo.class);
//				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				User user = new User();
				
				user.setId(request.getParameter("userId"));
				customerinfor.setCreatedBy(user.getId());
				customerinfor.setUserId(user.getId());
				String id =  JnpadCustomerInfoInsertServ‎ice.customerInforInsert(customerinfor);
				returnMap.put(RECORD_ID, id);
				returnMap.put(JRadConstants.MESSAGE, "添加成功");
					}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
				return json.toString();			
				}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();

	}
	
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse( HttpServletRequest request) {
		IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		Integer s =new Integer(userType);
		QueryResult<IntoPieces> result=null;
		//客户经理
		if(s==1){
			filter.setUserId(userId);
		}
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	//退回进件列表
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/returnToFirst.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String returnIntopiece( HttpServletRequest request) {
		IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		Integer s =new Integer(userType);
		QueryResult<IntoPieces> result=null;
		//客户经理
		if(s==1){
			filter.setUserId(userId);
		}
		filter.setStatus("returnedToFirst");
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	//拒绝进件列表
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/refuse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String refuseIntopiece( HttpServletRequest request) {
		IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		Integer s =new Integer(userType);
		QueryResult<IntoPieces> result=null;
		//客户经理
		if(s==1){
			filter.setUserId(userId);
		}
		filter.setStatus("refuse");
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}

	
	
	/**
	 * 客户资料录入---个人
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/insertgr.json", method = { RequestMethod.GET })
	public String customer_gr( @ModelAttribute CustomerPersonal customerpersonal,HttpServletRequest request) {
		String customerId=request.getParameter("customerId");
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<CustomerPersonal> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGr(customerId);
		try {
		if(custp.size()!=0){
			customerpersonal.setId(custp.get(0).getId());
			customerpersonal.setUpdatedate(new Date());
			JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoGr(customerpersonal);
			result.put("mess", "更新客户个人信息成功");
		}else{
			JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoGr(customerpersonal);
			result.put("mess", "添加客户个人信息成功");
			
		}
		} catch (Exception e) {
			result.put("mess", "操作失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	//查询客户个人信息
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/selectgr.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String selectGr( HttpServletRequest request) {
		String customerId=request.getParameter("customerId");
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<CustomerPersonal> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGr(customerId);
		CustomerPersonal customerpersonal =new CustomerPersonal();
		if(custp.size()!=0){
			customerpersonal=custp.get(0);
		}
		result.put("custp",customerpersonal);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 客户资料录入---房产
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/insertfc.json", method = { RequestMethod.GET })
	public String customer_fc( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			customerhouse.setCreateDate(new Date());
			JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoFc(customerhouse);
			result.put("mess", "添加客户房产信息成功");
		} catch (Exception e) {
			result.put("mess", "操作失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	//查询客户房产信息
		@ResponseBody
		@RequestMapping(value = "/ipad/customerIntopiece/selectfc.json", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public String selectFc( HttpServletRequest request) {
			String customerId=request.getParameter("customerId");
			HashMap<String, Object> result = new HashMap<String, Object>();
			List<CustomerHouse> customerhouse=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoFc(customerId);
			result.put("customerhouse",customerhouse);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(result, jsonConfig);
			return json.toString();
		}
		

		/**
		 * 客户资料录入---家庭
		 * 
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/customerIntopiece/insertjt.json", method = { RequestMethod.GET })
		public String customer_jt( @ModelAttribute CustomerFamilyInfo customerfamilyinfo,HttpServletRequest request) {
			String customerId=request.getParameter("customerId");
			HashMap<String, Object> result = new HashMap<String, Object>();
			List<CustomerFamilyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJt(customerId);
			try {
			if(custp.size()!=0){
				customerfamilyinfo.setId(custp.get(0).getId());
				customerfamilyinfo.setUpdateDate(new Date());
				JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoJt(customerfamilyinfo);
				result.put("mess", "更新客户家庭信息成功");
			}else{
				JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoJt(customerfamilyinfo);
				result.put("mess", "添加客户家庭信息成功");
				
			}
			} catch (Exception e) {
				result.put("mess", e.getMessage());
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(result, jsonConfig);
			return json.toString();
		}
		
		//查询客户家庭信息
		@ResponseBody
		@RequestMapping(value = "/ipad/customerIntopiece/selectjt.json", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public String selectJt( HttpServletRequest request) {
			String customerId=request.getParameter("customerId");
			HashMap<String, Object> result = new HashMap<String, Object>();
			List<CustomerFamilyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJt(customerId);
			CustomerFamilyInfo customerfamilyinfo =new CustomerFamilyInfo();
			if(custp.size()!=0){
				customerfamilyinfo=custp.get(0);
			}
			result.put("cusf",customerfamilyinfo);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(result, jsonConfig);
			return json.toString();
		}
		
		/**
		 * 客户资料录入---车产
		 * 
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/customerIntopiece/insertcc.json", method = { RequestMethod.GET })
		public String customer_cc( @ModelAttribute CustomerCarInfo customercarinfo,HttpServletRequest request) {
			HashMap<String, Object> result = new HashMap<String, Object>();
			try {
				customercarinfo.setCreateDate(new Date());
				JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoCc(customercarinfo);
				result.put("mess", "添加客户车产信息成功");
			} catch (Exception e) {
				result.put("mess", "操作失败");
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(result, jsonConfig);
			return json.toString();
		}
		
		//查询客户车产信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectcc.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectcc( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerCarInfo> customercarinfo=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoCc(customerId);
				result.put("customercar",customercarinfo);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 客户资料录入---联系人
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/insertlxr.json", method = { RequestMethod.GET })
			public String customer_lxr( @ModelAttribute CustomerContact customercontact,HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				try {
					customercontact.setCreateDate(new Date());
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoLxr(customercontact);
					result.put("mess", "添加客户联系人信息成功");
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			//查询客户联系人信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectlxr.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectlxr( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerContact> customercarinfo=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoLxr(customerId);
				result.put("customercontact",customercarinfo);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 客户资料录入---居住
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/insertjz.json", method = { RequestMethod.GET })
			public String customer_jz( @ModelAttribute CustomerLiving customerliving,HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerLiving> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJz(customerId);
				try {
				if(custp.size()!=0){
					customerliving.setId(custp.get(0).getId());
					customerliving.setUpdateDate(new Date());
					JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoJz(customerliving);
					result.put("mess", "更新客居住信息成功");
				}else{
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoJz(customerliving);
					result.put("mess", "添加客户居住信息成功");
					
				}
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			//查询客户居住信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectjz.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectJz( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerLiving> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJz(customerId);
				CustomerLiving customerliving =new CustomerLiving();
				if(custp.size()!=0){
					customerliving=custp.get(0);
				}
				result.put("living",customerliving);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 客户资料录入---企业信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/insertqyxx.json", method = { RequestMethod.GET })
			public String customer_qyxx( @ModelAttribute CustomerCompanyInfo customercompanyinfo,HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerCompanyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyxx(customerId);
				try {
					if(custp.size()!=0){
						customercompanyinfo.setId(custp.get(0).getId());
						customercompanyinfo.setUpdateDate(new Date());
						JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoQyxx(customercompanyinfo);
						result.put("mess", "更新客户企业基本信息成功");
					}else{
						JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQyxx(customercompanyinfo);
						result.put("mess", "添加客户企业基本信息成功");
						
					}
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			//查询客户企业信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectqyxx.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectqyxx( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerCompanyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyxx(customerId);
				CustomerCompanyInfo customercompanyinfo =new CustomerCompanyInfo();
				if(custp.size()!=0){
					customercompanyinfo=custp.get(0);
				}
				result.put("company",customercompanyinfo);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 客户资料录入---企业业务信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/insertqyyw.json", method = { RequestMethod.GET })
			public String customer_qyyw( @ModelAttribute CustomerCompanyBusiness customercompanybusiness,HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerCompanyBusiness> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyyw(customerId);
				try {
					if(custp.size()!=0){
						customercompanybusiness.setId(custp.get(0).getId());
						customercompanybusiness.setUpdateDate(new Date());
						JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoQyyw(customercompanybusiness);
						result.put("mess", "更新客户企业业务信息成功");
					}else{
						JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQyyw(customercompanybusiness);
						result.put("mess", "添加客户企业业务信息成功");
						
					}
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			//查询客户企业业务信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectqyyw.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectqyyw( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerCompanyBusiness> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyyw(customerId);
				CustomerCompanyBusiness customercompanybusiness =new CustomerCompanyBusiness();
				if(custp.size()!=0){
					customercompanybusiness=custp.get(0);
				}
				result.put("business",customercompanybusiness);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 客户资料录入---企业店铺信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/insertqydp.json", method = { RequestMethod.GET })
			public String customer_qydp( @ModelAttribute CustomerStore customerstore,HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerStore> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				try {
					if(custp.size()!=0){
						customerstore.setId(custp.get(0).getId());
						customerstore.setUpdateDate(new Date());
						JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoQydp(customerstore);
						result.put("mess", "更新客户企业店铺或其他信息成功");
					}else{
						JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQydp(customerstore);
						result.put("mess", "添加客户企业店铺或其他信息成功");
						
					}
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			//查询客户企业店铺信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectqydp.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectqydp( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerStore> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				CustomerStore customerstore =new CustomerStore();
				if(custp.size()!=0){
					customerstore=custp.get(0);
				}
				result.put("dianpu",customerstore);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 客户资料录入---企业开户信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/insertqykh.json", method = { RequestMethod.GET })
			public String customer_qykh( @ModelAttribute CustomerBank customerbank,HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				try {
					customerbank.setCreateDate(new Date());
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQykh(customerbank);
					result.put("mess", "添加客户企业开户信息成功");
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			//查询客户企业开户信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectqykh.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectqykh( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<CustomerBank> customerbank=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQykh(customerId);
				result.put("kaihu",customerbank);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			//查询客户所有信息
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/selectAll.json", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public String selectAll( HttpServletRequest request) {
				String customerId=request.getParameter("customerId");
				HashMap<String, Object> result = new HashMap<String, Object>();
				
				List<CustomerBank> customerbank=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQykh(customerId);
				if(customerbank.size()!=0){
					result.put("kaihu","success");
					result.put("kaihu2","已录入");
					
				}else{
					result.put("kaihu","important");
					result.put("kaihu2","未录入");
				}
				result.put("kaihu3",customerbank);
				List<CustomerPersonal> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGr(customerId);
				if(custp.size()!=0){
					result.put("geren","success");
					result.put("geren2","已录入");
					result.put("geren3",custp.get(0));
				}else{
					result.put("geren","important");
					result.put("geren2","未录入");
					result.put("geren3",new CustomerPersonal());
				}
				List<CustomerHouse> customerhouse=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoFc(customerId);
				if(customerhouse.size()!=0){
					result.put("fangchan","success");
					result.put("fangchan2","已录入");
				}else{
					result.put("fangchan","important");
					result.put("fangchan2","未录入");
				}
				result.put("fangchan3",customerhouse);
				List<CustomerFamilyInfo> custf=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJt(customerId);
				if(custf.size()!=0){
					result.put("jiating","success");
					result.put("jiating2","已录入");
					result.put("jiating3",custf.get(0));
				}else{
					result.put("jiating","important");
					result.put("jiating2","未录入");
					result.put("jiating2","未录入");
					result.put("jiating3",new CustomerFamilyInfo());
				}
				List<CustomerCarInfo> customercarinfo=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoCc(customerId);
				if(customercarinfo.size()!=0){
					result.put("chechan","success");
					result.put("chechan2","已录入");
				}else{
					result.put("chechan","important");
					result.put("chechan2","未录入");
				}
				result.put("chechan3",customercarinfo);
				List<CustomerContact> lianxiren=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoLxr(customerId);
				if(lianxiren.size()!=0){
					result.put("lianxiren","success");
					result.put("lianxiren2","已录入");
				}else{
					result.put("lianxiren","important");
					result.put("lianxiren2","未录入");
				}
				result.put("lianxiren3",lianxiren);
				List<CustomerLiving> custjz=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJz(customerId);
				if(custjz.size()!=0){
					result.put("juzhu","success");
					result.put("juzhu2","已录入");
					result.put("juzhu3",custjz.get(0));
				}else{
					result.put("juzhu","important");
					result.put("juzhu2","未录入");
					result.put("juzhu3",new CustomerLiving());
				}
				List<CustomerCompanyInfo> custqyxx=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyxx(customerId);
				if(custqyxx.size()!=0){
					result.put("qyxx","success");
					result.put("qyxx2","已录入");
					result.put("qyxx3",custqyxx.get(0));
				}else{
					result.put("qyxx","important");
					result.put("qyxx2","未录入");
					result.put("qyxx3",new CustomerCompanyInfo());
				}
				List<CustomerCompanyBusiness> custqyyw=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyyw(customerId);
				if(custqyyw.size()!=0){
					result.put("qyyw","success");
					result.put("qyyw2","已录入");
					result.put("qyyw3",custqyyw.get(0));
				}else{
					result.put("qyyw","important");
					result.put("qyyw2","未录入");
					result.put("qyyw3",new CustomerCompanyBusiness());
				}
				List<CustomerStore> custpqydp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				if(custpqydp.size()!=0){
					result.put("qydp","success");
					result.put("qydp2","已录入");
					result.put("qydp3",custpqydp.get(0));
					if(custpqydp.get(0).getOtherInfo()!=""||custpqydp.get(0).getOtherInfo()!=null){
						result.put("qyqt","success");
						result.put("qyqt2","已录入");
					}else{
						result.put("qyqt","important");
						result.put("qyqt2","未录入");
					}
				}else{
					result.put("qydp","important");
					result.put("qydp2","未录入");
					result.put("qyqt","important");
					result.put("qyqt2","未录入");
					result.put("qydp3",new CustomerStore());
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
			/**
			 * 客户资删除
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/deleteInfo.json", method = { RequestMethod.GET })
			public String deleteinfo( HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				String id=request.getParameter("id");
				String tables=request.getParameter("tables");
				try {
						JnpadCustomerInfoInsertServ‎ice.deleteinfo(id,tables);
						result.put("mess","删除信息成功");
				} catch (Exception e) {
					result.put("mess","删除失败："+WebRequestHelper.processException(e));
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 修改---车产
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/updatecc.json", method = { RequestMethod.GET })
			public String updatecc( @ModelAttribute CustomerCarInfo customercarinfo,HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				try {
					JnpadCustomerInfoInsertServ‎ice.updateCustomerCarInfo(customercarinfo);
					result.put("mess", "修改客户车产信息成功");
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 修改---房产
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/updatefc.json", method = { RequestMethod.GET })
			public String updatefc( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				try {
					JnpadCustomerInfoInsertServ‎ice.updateCustomerInfoFc(customerhouse);
					result.put("mess", "修改客户房产信息成功");
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 修改---联系人
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/updatelxr.json", method = { RequestMethod.GET })
			public String updatelxr( @ModelAttribute CustomerContact customerContact,HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				try {
					JnpadCustomerInfoInsertServ‎ice.updateCustomerLxrInfo(customerContact);
					result.put("mess", "修改客户联系人信息成功");
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			/**
			 * 修改---企业开户
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/ipad/customerIntopiece/updateqykh.json", method = { RequestMethod.GET })
			public String updateqykh( @ModelAttribute CustomerBank customerBank,HttpServletRequest request) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				try {
					JnpadCustomerInfoInsertServ‎ice.updateCustomerQykhInfo(customerBank);
					result.put("mess", "修改客户企业开户信息成功");
				} catch (Exception e) {
					result.put("mess", "操作失败");
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(result, jsonConfig);
				return json.toString();
			}
			
}
