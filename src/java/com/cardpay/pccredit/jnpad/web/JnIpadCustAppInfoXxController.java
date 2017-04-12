package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.CustomerApprovedFilter;
import com.cardpay.pccredit.jnpad.filter.NotificationMessageFilter;
import com.cardpay.pccredit.jnpad.model.AppInfoListVo;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.DelayInfoForm;
import com.cardpay.pccredit.jnpad.model.JnpadCustomerBianGeng;
import com.cardpay.pccredit.jnpad.model.NotificationmMessage;
import com.cardpay.pccredit.jnpad.model.NotifyMsgListVo;
import com.cardpay.pccredit.jnpad.model.RetrainUserVo;
import com.cardpay.pccredit.jnpad.model.RetrainingVo;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.cardpay.pccredit.jnpad.service.JnpadRiskCustomerCollectionService;
import com.cardpay.pccredit.jnpad.service.JnpadZongBaoCustomerInsertService;
import com.cardpay.pccredit.manager.filter.RetrainingFilter;
import com.cardpay.pccredit.manager.model.AccountManagerRetraining;
import com.cardpay.pccredit.manager.model.Retraining;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.util.web.RequestHelper;

/**
 * ipad interface
 * 3.1.2 客户进件信息
 * 3.1.3 客户运营状况
 * 3.1.5 通知
 * 3.1.6 奖励激励信息
 * songchen
 * 2016年05月09日   下午1:54:18
 */
@Controller
public class JnIpadCustAppInfoXxController {
	
	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;

	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Autowired
	private JnpadRiskCustomerCollectionService JnpadriskCustomerCollectionService;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private JnpadZongBaoCustomerInsertService jnpadZongBaoCustomerInsertService;
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
		//当前登录用户ID
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
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/browse1.json", method = { RequestMethod.GET })
	public String browse1(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String userType=request.getParameter("userType");
		//refuse
//		String status2=request.getParameter("status2");
		String status2="refuse";
		//approved
//		String status3=request.getParameter("status3");
		String status3="approved";
		Integer s =new Integer(userType);
		if(s!=1){
			userId="";
		}
		IntoPiecesFilter filter=new IntoPiecesFilter();
		filter.setUserId(userId);
		int refuse = appInfoXxService.findCustAppInfoXxCount(userId,null,status2, null,null);
		int approved = appInfoXxService.findCustAppInfoXxCount(userId,null,null, status3,null);
		int sum = intoPiecesComdao.findintoPiecesByFilterCount(filter);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		
		AppInfoListVo vo = new AppInfoListVo();
		vo.setApprovedNum(approved);
		vo.setRefuseNum(refuse);
		
		result.put("result", vo);
		result.put("sums", sum);
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
		//当前登录用户ID
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
		//当前登录用户ID
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
	public String updateCustomerApplicationInfo(HttpServletRequest request,CustomerApprovedFilter filter) {
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
		//当前登录用户ID
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
		//当前登录用户ID
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
		//当前登录用户ID
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
	
	/**
	 * 客户资料变更通知
	 * 根据参数NotificationMessageFilter的noticeType属性的不同来实现查询不同的通知
	 * //审贷会通知shendaihui//客户原始资料变更通知yuanshiziliao
	 * //培训通知peixun//考察成绩通知kaocha//其他通知qita
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/notifiyMessage.json", method = { RequestMethod.GET })
	public String notifiyMessage(HttpServletRequest request,NotificationMessageFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String noticeType=request.getParameter("noticeType");
		filter.setUserId(userId);
		filter.setNoticeType(noticeType);
		
		List<NotificationMessage> list = appInfoXxService.findNotificationMessageByFilter(filter);
		int totalCount = appInfoXxService.findNotificationCountMessageByFilter(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/notifiyMessageNum.json", method = { RequestMethod.GET })
	public String notifiyMessageNum(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
	
		NotificationMessageFilter filter = new NotificationMessageFilter();
		filter.setUserId(userId);
		filter.setNoticeType("shendaihui");
		int count1 = appInfoXxService.findNotificationCountMessageByFilter(filter);
//		filter.setNoticeType("yuanshiziliao");
//		int count2 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		filter.setNoticeType("peixun");
		int count3 = appInfoXxService.findNotificationCountMessageByFilter(filter);
//		filter.setNoticeType("kaocha");
//		int count4 = appInfoXxService.findNotificationCountMessageByFilter(filter);
//		filter.setNoticeType("qita");
//		int count5 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		
		//拒绝进件数量
		filter.setNoticeType("refuse");
		int refuseCount= appInfoXxService.findNoticeCountByFilter(filter);
		//补充调查通知
		filter.setNoticeType("returnedToFirst");
		int returnCount= appInfoXxService.findNoticeCountByFilter(filter);
		//风险客户通知
		RiskCustomerFilter filters = new RiskCustomerFilter();
		filters.setCustManagerId(userId);
		filters.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
	    filters.setRole(RiskControlRole.manager.toString());
		int risk = appInfoXxService.findRiskNoticeCountByFilter(filters);
		//客户资料变更
		List<CustomerInfor> cuslist=appInfoXxService.findbiangengCountByManagerId(userId);
		int count6 = cuslist.size();
		//分配进件通知
		String managerIds =null;
		List<CustomerInfo> customerList = jnpadZongBaoCustomerInsertService.selectCustomerInfo(managerIds);
		int count5 = customerList.size();
		/*逾期催收*/
		String managerId = null;
		String userType = request.getParameter("userType");
		if(userType.equals("1")){
			managerId = request.getParameter("userId");
		}
		List<DelayInfoForm> cunstomerList =JnpadriskCustomerCollectionService.getCustomerRiskInfo(managerId);
		int yq_remind_message = cunstomerList.size();
		int sum=count1+count3+count5+refuseCount+returnCount+risk+count6+yq_remind_message;
		NotifyMsgListVo vo  = new NotifyMsgListVo();
		vo.setShendaihui(count1);
//		vo.setYuanshiziliao(count2);
		vo.setPeixun(count3);
//		vo.setKaocha(count4);
		vo.setQita(count5);
		vo.setRefuseCount(refuseCount);
		vo.setReturnCount(returnCount);
		vo.setRisk(risk);
		vo.setSum(sum);
		vo.setZiliaobiangeng(count6);
		vo.setBianggeng(cuslist);
		vo.setYuqi(yq_remind_message);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(vo, jsonConfig);
		return json.toString();
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findRetraining.json", method = { RequestMethod.GET })
	public String findRetraining(HttpServletRequest request,RetrainingFilter filter) {
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
		
		
		List<RetrainingVo> list = appInfoXxService.findRetrainingsVoByFilter(filter);
		for(RetrainingVo vo :list){
			List<String> userList = appInfoXxService.findAccountManagerRetraining(vo.getId());
			SystemUser user = appInfoXxService.findSysUserById(vo.getCreatedBy());
			vo.setCreatedBy(user.getDisplayName());
			vo.setUserList(userList);
		}
		
		int totalCount = appInfoXxService.findRetrainingsCountByFilter(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 奖励激励信息
	 * 上个月奖励激励金额
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/rewardIncentive.json", method = { RequestMethod.GET })
	public String rewardIncentive(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		
		String  reward_incentive = appInfoXxService.getRewardIncentiveInformation(Integer.parseInt(year),
																				  Integer.parseInt(month),
																				  userId);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("reward_incentive",reward_incentive);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	/**
	 * 奖励激励信息
	 * 风险保证
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/returnPrepareAmount.json", method = { RequestMethod.GET })
	public String returnPrepareAmount(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String userType=request.getParameter("userType");
		String  return_prepare_amount="";
		if(userType.equals("1")){
			return_prepare_amount = appInfoXxService.getReturnPrepareAmountById(userId);
		}else{
			return_prepare_amount = appInfoXxService.getSumReturnPrepareAmountById(userId);
			userId=null;
		}
		/*奖励激励状况*/
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		if(month == 0){
			year = year - 1;
			month = 12;
		}
		/*奖励激励****************************************************************************/
		String reward_incentive = appInfoXxService.getRewardIncentiveInformation(year, month, userId);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("return_prepare_amount",return_prepare_amount);
		result.put("reward_incentive",reward_incentive);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/changestate.json", method = { RequestMethod.GET })
	public String change(HttpServletRequest request) {
		//当前登录用户ID
		String id=request.getParameter("id");
		String cardId=request.getParameter("cardId");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			appInfoXxService.changeIsLook(id,cardId);
			result.put("mess","操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("mess","操作失败");
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	//创建审贷会通知
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/cjshendaihuitz.json", method = { RequestMethod.GET })
	public String shendaihuitz(@ModelAttribute NotificationmMessage notificationmmessage,HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			String id = IDGenerator.generateID();
			notificationmmessage.setId(id);
			SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
			String ss= request.getParameter("zhidingdate");
			Date modifiedTime =sf.parse(ss);
			notificationmmessage.setModifiedTime(modifiedTime);
			notificationmmessage.setCreatedTime(new Date());
			appInfoXxService.insertShendaiTongzhi(notificationmmessage);
			
			result.put("mess","操作成功");
		} catch (Exception e) {
			result.put("mess","操作失败");
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	//查询审贷会通知
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/cxshendaihuitz.json", method = { RequestMethod.GET })
	public String cxshendaihuitz(HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		String managerId=request.getParameter("managerId");
		List<NotificationmMessage> notificationmmessage =appInfoXxService.selectshendaihuitz(managerId) ;
		result.put("result", notificationmmessage);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	//改变审贷会通知状态
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/changesdhtzstatus.json", method = { RequestMethod.GET })
	public String changesdh(HttpServletRequest request) {
		//当前登录用户ID
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			appInfoXxService.changeSdhIsLook(id,status);
			result.put("mess","操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("mess","操作失败");
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
}
