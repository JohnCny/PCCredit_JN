package com.cardpay.pccredit.customer.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerBank;
import com.cardpay.pccredit.jnpad.model.CustomerCarInfo;
import com.cardpay.pccredit.jnpad.model.CustomerCompanyBusiness;
import com.cardpay.pccredit.jnpad.model.CustomerCompanyInfo;
import com.cardpay.pccredit.jnpad.model.CustomerContact;
import com.cardpay.pccredit.jnpad.model.CustomerFamilyInfo;
import com.cardpay.pccredit.jnpad.model.CustomerHouse;
import com.cardpay.pccredit.jnpad.model.CustomerJob;
import com.cardpay.pccredit.jnpad.model.CustomerLiving;
import com.cardpay.pccredit.jnpad.model.CustomerPersonal;
import com.cardpay.pccredit.jnpad.model.CustomerStore;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerInfoInsertServ‎ice;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/customer/firsthend/*")
@JRadModule("customer.firsthend")
public class CustomerDetailedInfoController extends BaseController{

	
	@Autowired
	private JnpadCustomerInfoInsertServ‎ice JnpadCustomerInfoInsertServ‎ice;
	
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 客户资料录入---个人
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertgr.json", method = { RequestMethod.GET })
	public JRadReturnMap customer_gr( @ModelAttribute CustomerPersonal customerpersonal,HttpServletRequest request) {
		String customerId=request.getParameter("id");
		customerpersonal.setCustomerId(customerId);
		JRadReturnMap returnMap = new JRadReturnMap();
		List<CustomerPersonal> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGr(customerId);
		try {
		if(custp.size()!=0){
			customerpersonal.setId(custp.get(0).getId());
			customerpersonal.setUpdatedate(new Date());
			JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoGr(customerpersonal);
			returnMap.put("message","更新客户个人信息成功");
		}else{
			JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoGr(customerpersonal);
			returnMap.put("message","添加客户个人信息成功");
			
		}
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		returnMap.put("customerNm", customerId);
		return returnMap;
	}
	
	//查询客户个人信息
	@ResponseBody
	@RequestMapping(value = "selectgr.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView selectGr( HttpServletRequest request) {
		String customerId=request.getParameter("id");
		JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_gr", request);
		//查询权限 非本人只能查看 不能操作
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		boolean lock = false;
		if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
			lock = true;
		}
		mv.addObject("lock", lock);
		List<CustomerPersonal> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGr(customerId);
		CustomerPersonal customerpersonal =new CustomerPersonal();
		if(custp.size()!=0){
			customerpersonal=custp.get(0);
		}
		mv.addObject("custp",customerpersonal);
		
		mv.addObject("customerNm", customerId);
		return mv;
	}
	
	
	/**
	 * 客户资料录入---房产
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertfc.json", method = { RequestMethod.GET })
	public JRadReturnMap customer_fc( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String customerId=request.getParameter("id");
		String monetaryDateString=request.getParameter("monetaryDateString");
		try {
			customerhouse.setCreateDate(new Date());
			customerhouse.setCustomerId(customerId);
			customerhouse.setMonetaryDate(DateHelper.getDateFormat(monetaryDateString, "yyyy-MM-dd HH:mm:ss"));
			JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoFc(customerhouse);
			returnMap.put("message","添加客户房产信息成功");
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		returnMap.put("customerNm", customerId);
		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "updatefc.json", method = { RequestMethod.GET })
	public JRadReturnMap customer_fc_upd( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String customerId=request.getParameter("custId");
		String monetaryDateString=request.getParameter("monetaryDateString");
		try {
			customerhouse.setCustomerId(customerId);
			customerhouse.setMonetaryDate(DateHelper.getDateFormat(monetaryDateString, "yyyy-MM-dd HH:mm:ss"));
			JnpadCustomerInfoInsertServ‎ice.updateCustomerInfoFc(customerhouse);
			returnMap.put("message","修改客户房产信息成功");
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		returnMap.put("customerNm", customerId);
		return returnMap;
	}
	//房产录入页面
	@ResponseBody
	@RequestMapping(value = "insertfc_browse.page", method = { RequestMethod.GET })
	public AbstractModelAndView customer_fc_browse( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
		String customerId=request.getParameter("id");
		JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_fc_add", request);
		mv.addObject("customerNm",customerId);
		return mv;		
	}
	
	@ResponseBody
	@RequestMapping(value = "updatefc_browse.page", method = { RequestMethod.GET })
	public AbstractModelAndView customer_fc_update_browse( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
		String id=request.getParameter("id");
		String customerId=request.getParameter("customerId");
		JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_fc_update", request);
		 List<CustomerHouse> list = JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoFcById(id);
		if(list!=null&&list.size()>0){
			mv.addObject("fc",list.get(0));
		}
		mv.addObject("customerNm",customerId);
		return mv;		
	}
	
	//查询客户房产信息
		@ResponseBody
		@RequestMapping(value = "selectfc.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView selectFc( HttpServletRequest request) {
			String customerId=request.getParameter("id");
			JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_fc", request);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
			List<CustomerHouse> customerhouse=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoFc(customerId);
			mv.addObject("customerhouse",customerhouse);
			mv.addObject("customerNm",customerId);
			return mv;
		}
		

		/**
		 * 客户资料录入---家庭
		 * 
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "insertjt.json", method = { RequestMethod.GET })
		public JRadReturnMap customer_jt( @ModelAttribute CustomerFamilyInfo customerfamilyinfo,HttpServletRequest request) {
			String customerId=request.getParameter("id");
			customerfamilyinfo.setCustomerId(customerId);
			JRadReturnMap returnMap = new JRadReturnMap();
			List<CustomerFamilyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJt(customerId);
			try {
			if(custp.size()!=0){
				customerfamilyinfo.setId(custp.get(0).getId());
				customerfamilyinfo.setUpdateDate(new Date());
				JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoJt(customerfamilyinfo);
				returnMap.put("message","更新客户家庭信息成功");
			}else{
				JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoJt(customerfamilyinfo);
				returnMap.put("message","添加客户家庭信息成功");
				
			}
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
			returnMap.put("customerNm", customerId);
			return returnMap;
		}
		
		//查询客户家庭信息
		@ResponseBody
		@RequestMapping(value = "selectjt.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView selectJt( HttpServletRequest request) {
			String customerId=request.getParameter("id");
			
			JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_jt", request);
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
			List<CustomerFamilyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJt(customerId);
			CustomerFamilyInfo customerfamilyinfo =new CustomerFamilyInfo();
			if(custp.size()!=0){
				customerfamilyinfo=custp.get(0);
			}
			mv.addObject("cusf",customerfamilyinfo);
			mv.addObject("customerNm",customerId);
			return mv;
		}
		
		/**
		 * 客户资料录入---车产
		 * 
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "insertcc.json", method = { RequestMethod.GET })
		public JRadReturnMap customer_cc( @ModelAttribute CustomerCarInfo customercarinfo,HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			String customerId=request.getParameter("id");
			String monetaryDateString=request.getParameter("monetaryDateString");
			
			try {
				customercarinfo.setCreateDate(new Date());
				customercarinfo.setCustomerId(customerId);
				customercarinfo.setMonetaryDate(DateHelper.getDateFormat(monetaryDateString, "yyyy-MM-dd HH:mm:ss"));
				JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoCc(customercarinfo);
				returnMap.put("message","添加客户车产信息成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
			returnMap.put("customerNm", customerId);
			return returnMap;
		}
		
		//车产录入页面
		@ResponseBody
		@RequestMapping(value = "insertcc_browse.page", method = { RequestMethod.GET })
		public AbstractModelAndView customer_cc_browse( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
			String customerId=request.getParameter("id");
			JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_cc_add", request);
			mv.addObject("customerNm",customerId);
			return mv;		
		}
		
		@ResponseBody
		@RequestMapping(value = "updatecc_browse.page", method = { RequestMethod.GET })
		public AbstractModelAndView customer_cc_update_browse( @ModelAttribute CustomerCarInfo CustomerCarInfo,HttpServletRequest request) {
			String id=request.getParameter("id");
			String customerId=request.getParameter("customerId");
			JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_cc_update", request);
			 List<CustomerCarInfo> list = JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoccById(id);
			if(list!=null&&list.size()>0){
				mv.addObject("cc",list.get(0));
			}
			mv.addObject("customerNm",customerId);
			return mv;		
		}
		
		@ResponseBody
		@RequestMapping(value = "updatecc.json", method = { RequestMethod.GET })
		public JRadReturnMap customer_cc_upd( @ModelAttribute CustomerCarInfo customerCarInfo,HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			String customerId=request.getParameter("custId");
			String monetaryDateString=request.getParameter("monetaryDateString");
			try {
				customerCarInfo.setCustomerId(customerId);
				customerCarInfo.setMonetaryDate(DateHelper.getDateFormat(monetaryDateString, "yyyy-MM-dd HH:mm:ss"));
				JnpadCustomerInfoInsertServ‎ice.updateCustomerCarInfo(customerCarInfo);
				returnMap.put("message","修改客户车产信息成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
			returnMap.put("customerNm", customerId);
			return returnMap;
		}
		
		//查询客户车产信息
			@ResponseBody
			@RequestMapping(value = "selectcc.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectcc( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_cc", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerCarInfo> customercarinfo=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoCc(customerId);
				mv.addObject("customercar",customercarinfo);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			/**
			 * 客户资料录入---联系人
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertlxr.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_lxr( @ModelAttribute CustomerContact customercontact,HttpServletRequest request) {
				JRadReturnMap returnMap = new JRadReturnMap();
				String customerId=request.getParameter("id");
				try {
					customercontact.setCreateDate(new Date());
					customercontact.setCustomerId(customerId);
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoLxr(customercontact);
					returnMap.put("message","添加客户联系人信息成功");
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//联系人录入页面
			@ResponseBody
			@RequestMapping(value = "insertlxr_browse.page", method = { RequestMethod.GET })
			public AbstractModelAndView customer_lxr_browse( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_lxr_add", request);
				mv.addObject("customerNm",customerId);
				return mv;		
			}
			
			
			@ResponseBody
			@RequestMapping(value = "updatelxr_browse.page", method = { RequestMethod.GET })
			public AbstractModelAndView customer_lxr_update_browse( @ModelAttribute CustomerContact customerContact,HttpServletRequest request) {
				String id=request.getParameter("id");
				String customerId=request.getParameter("customerId");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_lxr_update", request);
				 List<CustomerContact> list = JnpadCustomerInfoInsertServ‎ice.selectCustomerInfolxrById(id);
				if(list!=null&&list.size()>0){
					mv.addObject("lxr",list.get(0));
				}
				mv.addObject("customerNm",customerId);
				return mv;		
			}
			
			@ResponseBody
			@RequestMapping(value = "updatelxr.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_lxr_upd( @ModelAttribute CustomerContact customerContact,HttpServletRequest request) {
				JRadReturnMap returnMap = new JRadReturnMap();
				String customerId=request.getParameter("custId");
				try {
					customerContact.setCustomerId(customerId);
					JnpadCustomerInfoInsertServ‎ice.updateCustomerLxrInfo(customerContact);
					returnMap.put("message","修改联系人信息成功");
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户联系人信息
			@ResponseBody
			@RequestMapping(value = "selectlxr.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectlxr( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_lxr", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerContact> customercarinfo=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoLxr(customerId);
				mv.addObject("customercontact",customercarinfo);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			/**
			 * 客户资料录入---居住
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertjz.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_jz( @ModelAttribute CustomerLiving customerliving,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				customerliving.setCustomerId(customerId);
				JRadReturnMap returnMap = new JRadReturnMap();
				String beginDateString=request.getParameter("beginDateString");
				customerliving.setBeginDate(DateHelper.getDateFormat(beginDateString, "yyyy-MM-dd HH:mm:ss"));
				List<CustomerLiving> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJz(customerId);
				try {
				if(custp.size()!=0){
					customerliving.setId(custp.get(0).getId());
					customerliving.setUpdateDate(new Date());
					JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoJz(customerliving);
					returnMap.put("message","更新客居住信息成功");
				}else{
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoJz(customerliving);
					returnMap.put("message","添加客户居住信息成功");
					
				}
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户居住信息
			@ResponseBody
			@RequestMapping(value = "selectjz.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectJz( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_jz", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerLiving> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJz(customerId);
				CustomerLiving customerliving =new CustomerLiving();
				if(custp.size()!=0){
					customerliving=custp.get(0);
				}
				mv.addObject("living",customerliving);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			/**
			 * 客户资料录入---企业信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertqyxx.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_qyxx( @ModelAttribute CustomerCompanyInfo customercompanyinfo,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				customercompanyinfo.setCustomerId(customerId);
				JRadReturnMap returnMap = new JRadReturnMap();
				String beginDateString=request.getParameter("beginDateString");
				customercompanyinfo.setBeginDate(DateHelper.getDateFormat(beginDateString, "yyyy-MM-dd HH:mm:ss"));
				List<CustomerCompanyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyxx(customerId);
				try {
					if(custp.size()!=0){
						customercompanyinfo.setId(custp.get(0).getId());
						customercompanyinfo.setUpdateDate(new Date());
						JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoQyxx(customercompanyinfo);
						returnMap.put("message","更新客企业基本信息成功");
					}else{
						JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQyxx(customercompanyinfo);
						returnMap.put("message","添加客户企业基本信息成功");
						
					}
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户企业信息
			@ResponseBody
			@RequestMapping(value = "selectqyxx.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectqyxx( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qyxx", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerCompanyInfo> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyxx(customerId);
				CustomerCompanyInfo customercompanyinfo =new CustomerCompanyInfo();
				if(custp.size()!=0){
					customercompanyinfo=custp.get(0);
				}
				mv.addObject("company",customercompanyinfo);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			/**
			 * 客户资料录入---企业业务信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertqyyw.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_qyyw( @ModelAttribute CustomerCompanyBusiness customercompanybusiness,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				customercompanybusiness.setCustomerId(customerId);
				JRadReturnMap returnMap = new JRadReturnMap();
				List<CustomerCompanyBusiness> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyyw(customerId);
				try {
					if(custp.size()!=0){
						customercompanybusiness.setId(custp.get(0).getId());
						customercompanybusiness.setUpdateDate(new Date());
						JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoQyyw(customercompanybusiness);
						returnMap.put("message","更新客企业业务信息成功");
					}else{
						JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQyyw(customercompanybusiness);
						returnMap.put("message","添加客户企业业务信息成功");
						
					}
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户企业业务信息
			@ResponseBody
			@RequestMapping(value = "selectqyyw.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectqyyw( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qyyw", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerCompanyBusiness> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyyw(customerId);
				CustomerCompanyBusiness customercompanybusiness =new CustomerCompanyBusiness();
				if(custp.size()!=0){
					customercompanybusiness=custp.get(0);
				}
				mv.addObject("business",customercompanybusiness);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			/**
			 * 客户资料录入---企业店铺信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertqydp.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_qydp( @ModelAttribute CustomerStore customerstore,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				customerstore.setCustomerId(customerId);
				JRadReturnMap returnMap = new JRadReturnMap();
				String beginDateString=request.getParameter("beginDateString");
				if(!StringUtils.isEmpty(beginDateString)){
					customerstore.setBeginDate(DateHelper.getDateFormat(beginDateString, "yyyy-MM-dd HH:mm:ss"));
				}
				List<CustomerStore> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				try {
					if(custp.size()!=0){
						customerstore.setId(custp.get(0).getId());
						customerstore.setUpdateDate(new Date());
						JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoQydp(customerstore);
						returnMap.put("message","更新客企业店铺或其他信息成功");
					}else{
						JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQydp(customerstore);
						returnMap.put("message","添加客户企业店铺或其他信息成功");
						
					}
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			/**
			 * 客户资料删除
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "deleteinfo.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_delete(HttpServletRequest request) {
				JRadReturnMap returnMap = new JRadReturnMap();
				String id=request.getParameter("id");
				String customerId=request.getParameter("customerId");
				String tables=request.getParameter("tables");
				try {
						JnpadCustomerInfoInsertServ‎ice.deleteinfo(id,tables);
						returnMap.put("message","删除信息成功");
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户企业店铺信息
			@ResponseBody
			@RequestMapping(value = "selectqydp.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectqydp( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qydp", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerStore> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				CustomerStore customerstore =new CustomerStore();
				if(custp.size()!=0){
					customerstore=custp.get(0);
				}
				mv.addObject("dianpu",customerstore);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			//查询客户企业店铺信息
			@ResponseBody
			@RequestMapping(value = "selectqyqt.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectqyqt( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qyqt", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerStore> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				CustomerStore customerstore =new CustomerStore();
				if(custp.size()!=0){
					customerstore=custp.get(0);
				}
				mv.addObject("dianpu",customerstore);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			/**
			 * 客户资料录入---企业开户信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertqykh.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_qykh( @ModelAttribute CustomerBank customerbank,HttpServletRequest request) {
				JRadReturnMap returnMap = new JRadReturnMap();
				String customerId=request.getParameter("id");
				try {
					customerbank.setCreateDate(new Date());
					customerbank.setCustomerId(customerId);
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoQykh(customerbank);
					returnMap.put("message","添加客户企业开户信息成功");
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm",customerId);
				return returnMap;
			}
			
			//企业开户录入页面
			@ResponseBody
			@RequestMapping(value = "insertqykh_browse.page", method = { RequestMethod.GET })
			public AbstractModelAndView customer_qykh_browse( @ModelAttribute CustomerHouse customerhouse,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qykh_add", request);
				mv.addObject("customerNm",customerId);
				return mv;		
			}
			
			@ResponseBody
			@RequestMapping(value = "updateqykh_browse.page", method = { RequestMethod.GET })
			public AbstractModelAndView customer_qykh_update_browse( @ModelAttribute CustomerBank customerBank,HttpServletRequest request) {
				String id=request.getParameter("id");
				String customerId=request.getParameter("customerId");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qykh_update", request);
				 List<CustomerBank> list = JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQykhById(id);
				if(list!=null&&list.size()>0){
					mv.addObject("lxr",list.get(0));
				}
				mv.addObject("customerNm",customerId);
				return mv;		
			}
			
			@ResponseBody
			@RequestMapping(value = "updateqykh.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_qykh_upd( @ModelAttribute CustomerBank customerBank,HttpServletRequest request) {
				JRadReturnMap returnMap = new JRadReturnMap();
				String customerId=request.getParameter("custId");
				try {
					customerBank.setCustomerId(customerId);
					JnpadCustomerInfoInsertServ‎ice.updateCustomerQykhInfo(customerBank);
					returnMap.put("message","修改企业开户信息成功");
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户企业开户信息
			@ResponseBody
			@RequestMapping(value = "selectqykh.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectqykh( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_qykh", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerBank> customerbank=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQykh(customerId);
				mv.addObject("kaihu",customerbank);
				mv.addObject("customerNm",customerId);
				return mv;
			}
			//查询客户所有信息
			@ResponseBody
			@RequestMapping(value = "selectAll.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectAll( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_all", request);
				
				List<CustomerBank> customerbank=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQykh(customerId);
				if(customerbank.size()!=0){
					mv.addObject("kaihu","success");
					mv.addObject("kaihu2","已录入");
					
				}else{
					mv.addObject("kaihu","important");
					mv.addObject("kaihu2","未录入");
				}
				mv.addObject("kaihu3",customerbank);
				List<CustomerPersonal> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGr(customerId);
				if(custp.size()!=0){
					mv.addObject("geren","success");
					mv.addObject("geren2","已录入");
					mv.addObject("geren3",custp.get(0));
				}else{
					mv.addObject("geren","important");
					mv.addObject("geren2","未录入");
					mv.addObject("geren3",new CustomerPersonal());
				}
				List<CustomerHouse> customerhouse=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoFc(customerId);
				if(customerhouse.size()!=0){
					mv.addObject("fangchan","success");
					mv.addObject("fangchan2","已录入");
				}else{
					mv.addObject("fangchan","important");
					mv.addObject("fangchan2","未录入");
				}
				mv.addObject("fangchan3",customerhouse);
				List<CustomerFamilyInfo> custf=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJt(customerId);
				if(custf.size()!=0){
					mv.addObject("jiating","success");
					mv.addObject("jiating2","已录入");
					mv.addObject("jiating3",custf.get(0));
				}else{
					mv.addObject("jiating","important");
					mv.addObject("jiating2","未录入");
					mv.addObject("jiating2","未录入");
					mv.addObject("jiating3",new CustomerFamilyInfo());
				}
				List<CustomerCarInfo> customercarinfo=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoCc(customerId);
				if(customercarinfo.size()!=0){
					mv.addObject("chechan","success");
					mv.addObject("chechan2","已录入");
				}else{
					mv.addObject("chechan","important");
					mv.addObject("chechan2","未录入");
				}
				mv.addObject("chechan3",customercarinfo);
				List<CustomerContact> lianxiren=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoLxr(customerId);
				if(lianxiren.size()!=0){
					mv.addObject("lianxiren","success");
					mv.addObject("lianxiren2","已录入");
				}else{
					mv.addObject("lianxiren","important");
					mv.addObject("lianxiren2","未录入");
				}
				mv.addObject("lianxiren3",lianxiren);
				List<CustomerLiving> custjz=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoJz(customerId);
				if(custjz.size()!=0){
					mv.addObject("juzhu","success");
					mv.addObject("juzhu2","已录入");
					mv.addObject("juzhu3",custjz.get(0));
				}else{
					mv.addObject("juzhu","important");
					mv.addObject("juzhu2","未录入");
					mv.addObject("juzhu3",new CustomerLiving());
				}
				List<CustomerCompanyInfo> custqyxx=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyxx(customerId);
				if(custqyxx.size()!=0){
					mv.addObject("qyxx","success");
					mv.addObject("qyxx2","已录入");
					mv.addObject("qyxx3",custqyxx.get(0));
				}else{
					mv.addObject("qyxx","important");
					mv.addObject("qyxx2","未录入");
					mv.addObject("qyxx3",new CustomerCompanyInfo());
				}
				List<CustomerCompanyBusiness> custqyyw=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQyyw(customerId);
				if(custqyyw.size()!=0){
					mv.addObject("qyyw","success");
					mv.addObject("qyyw2","已录入");
					mv.addObject("qyyw3",custqyyw.get(0));
				}else{
					mv.addObject("qyyw","important");
					mv.addObject("qyyw2","未录入");
					mv.addObject("qyyw3",new CustomerCompanyBusiness());
				}
				List<CustomerStore> custpqydp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoQydp(customerId);
				if(custpqydp.size()!=0){
					mv.addObject("qydp","success");
					mv.addObject("qydp2","已录入");
					mv.addObject("qydp3",custpqydp.get(0));
					if(custpqydp.get(0).getOtherInfo()!=""||custpqydp.get(0).getOtherInfo()!=null){
						mv.addObject("qyqt","success");
						mv.addObject("qyqt2","已录入");
					}else{
						mv.addObject("qyqt","important");
						mv.addObject("qyqt2","未录入");
					}
				}else{
					mv.addObject("qydp","important");
					mv.addObject("qydp2","未录入");
					mv.addObject("qyqt","important");
					mv.addObject("qyqt2","未录入");
					mv.addObject("qydp3",new CustomerStore());
				}
				mv.addObject("customerNm",customerId);
				return mv;
			}
			
			/**
			 * 客户资料录入---工薪信息
			 * 
			 * @param filter
			 * @param request
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "insertgxxx.json", method = { RequestMethod.GET })
			public JRadReturnMap customer_gxxx( @ModelAttribute CustomerJob customerjob,HttpServletRequest request) {
				String customerId=request.getParameter("id");
				customerjob.setCustomerId(customerId);
				JRadReturnMap returnMap = new JRadReturnMap();
				List<CustomerJob> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGxxx(customerId);
				try {
				if(custp.size()!=0){
					customerjob.setId(custp.get(0).getId());
					customerjob.setUpdateDate(new Date());
					JnpadCustomerInfoInsertServ‎ice.updatetCustomerInfoGxxx(customerjob);
					returnMap.put("message","更新客户工薪类信息成功");
				}else{
					JnpadCustomerInfoInsertServ‎ice.insertCustomerInfoGxxx(customerjob);
					returnMap.put("message","添加客户工薪类信息成功");
					
				}
				} catch (Exception e) {
					return WebRequestHelper.processException(e);
				}
				returnMap.put("customerNm", customerId);
				return returnMap;
			}
			
			//查询客户工薪信息
			@ResponseBody
			@RequestMapping(value = "selectgxxx.page", method = { RequestMethod.GET })
			@JRadOperation(JRadOperation.BROWSE)
			public AbstractModelAndView selectGxxx( HttpServletRequest request) {
				String customerId=request.getParameter("id");
				JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_gxxx", request);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(customerId).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
				List<CustomerJob> custp=JnpadCustomerInfoInsertServ‎ice.selectCustomerInfoGxxx(customerId);
				CustomerJob customerpersonal =new CustomerJob();
				if(custp.size()!=0){
					customerpersonal=custp.get(0);
				}
				mv.addObject("custp",customerpersonal);
				
				mv.addObject("customerNm", customerId);
				return mv;
			}
}
