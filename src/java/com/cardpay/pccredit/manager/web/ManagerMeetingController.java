package com.cardpay.pccredit.manager.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.filter.ManagerMeetingFilter;
import com.cardpay.pccredit.manager.model.InformationOfficer;
import com.cardpay.pccredit.manager.model.ManagerMeetingInfo;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceModel;
import com.cardpay.pccredit.manager.service.ManagerMeetingService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
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

@Controller
@RequestMapping("/manager/meeting/*")
@JRadModule("manager.meeting")
public class ManagerMeetingController extends BaseController {
	@Autowired
	ManagerMeetingService managerMeetingService;
	
	/**
	 * 上会登记录入页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/meeting/insertmeeting", request);
		return mv;
	}
	
	
	/**
	 * 执行录入
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	public JRadReturnMap update(@ModelAttribute ManagerMeetingInfo managerMeetingInfo,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			managerMeetingInfo.setManagerId(user.getId());
			managerMeetingInfo.setManagerName(user.getDisplayName());
			String id = IDGenerator.generateID();
			managerMeetingInfo.setId(id);
			managerMeetingInfo.setCreateDate(new Date());
			managerMeetingService.insertMeetingInfo(managerMeetingInfo);
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.put("mess", "提交成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}
		return returnMap;
	}
	
	/**
	 * 查询上会登记结果
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "select.page")
	public AbstractModelAndView select(@ModelAttribute ManagerMeetingFilter filter,HttpServletRequest request) { 
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		if(user.getUserType()==1){
			filter.setManagerId(user.getId());
		}
		QueryResult<ManagerMeetingInfo> result = managerMeetingService.findManagerMeetingByFilter(filter);
		JRadPagedQueryResult<ManagerMeetingInfo> pagedResult = new JRadPagedQueryResult<ManagerMeetingInfo>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/meeting/selectmeeting", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("userType", user.getUserType());
		return mv;
	}
	/**
	 * 导出上会登记结果
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "export.json")
	public JRadReturnMap export(@ModelAttribute ManagerMeetingFilter filter,HttpServletRequest request,HttpServletResponse response) { 
		filter.setRequest(request);
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		if(user.getUserType()==1){
			filter.setManagerId(user.getId());
		}
		try {
			QueryResult<ManagerMeetingInfo> result = managerMeetingService.findManagerMeetingByFilter(filter);
			managerMeetingService.exportManagerMeeting(result,response,filter.getRegisterMeetingDate());
			returnMap.put("message", "导出成功");
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		
		return returnMap;
	}

	/**
	 * 上会登记修改页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView update(@ModelAttribute ManagerMeetingFilter filter,HttpServletRequest request) { 
		QueryResult<ManagerMeetingInfo> result = managerMeetingService.findManagerMeetingByFilter(filter);
		List<ManagerMeetingInfo> managermeetinginfo =result.getItems();
		JRadModelAndView mv = new JRadModelAndView("/manager/meeting/updatemeeting", request);
		ManagerMeetingInfo meetinfo =new ManagerMeetingInfo();
		if(managermeetinginfo.size()>0){
			meetinfo =managermeetinginfo.get(0);
		}
		mv.addObject("result",meetinfo);
		return mv;
	}
	/**
	 * 上会登记结果页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "dealresult.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView updateresult(@ModelAttribute ManagerMeetingFilter filter,HttpServletRequest request) { 
		QueryResult<ManagerMeetingInfo> result = managerMeetingService.findManagerMeetingByFilter(filter);
		List<ManagerMeetingInfo> managermeetinginfo =result.getItems();
		JRadModelAndView mv = new JRadModelAndView("/manager/meeting/dealmeetingresult", request);
		ManagerMeetingInfo meetinfo =new ManagerMeetingInfo();
		if(managermeetinginfo.size()>0){
			meetinfo =managermeetinginfo.get(0);
		}
		mv.addObject("result",meetinfo);
		return mv;
	}
	
	/**
	 * 执行更新
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	public JRadReturnMap updates(@ModelAttribute ManagerMeetingInfo managerMeetingInfo,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			managerMeetingInfo.setModifyDate(new Date());
			managerMeetingService.updateMeetingInfo(managerMeetingInfo);
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.put("mess", "更新成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "更新失败");
			returnMap.addGlobalMessage("保存失败");
		}
		return returnMap;
	}
}
