package com.cardpay.pccredit.manager.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.calcuation.utils.StringUtil;
import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.main.MainController;
import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;

import com.cardpay.pccredit.manager.form.VisitRegistLedgerForm;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;

import com.cardpay.pccredit.manager.model.LoanApproved;
import com.cardpay.pccredit.manager.model.LoanRefused;

import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceModel;

import com.cardpay.pccredit.manager.model.VisitRegistLedger;

import com.cardpay.pccredit.manager.service.ManagerOtherInfoInputService;

import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.cardpay.pccredit.manager.service.OtherMusidataInputService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/manager/otherinfoinput/*")
@JRadModule("manager.otherinfoinput")
public class ManagerOtherInfoInputController extends BaseController {
	private static final Logger logger = Logger.getLogger(ManagerOtherInfoInputController.class);
	@Autowired
	private ManagerOtherInfoInputService managerOtherInfoInputService;
	
	@Autowired
	private OtherMusidataInputService OtherMusidataInputService;

	/**
	 * 放款台账查询页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_0.page")
	public AbstractModelAndView iframe_0(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_0", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		
		filter.setRequest(request);
		QueryResult<LoanApproved> result = managerOtherInfoInputService.findLoanApprovedByFilter(filter);
		JRadPagedQueryResult<LoanApproved> pagedResult = new JRadPagedQueryResult<LoanApproved>(filter, result);
		mv.addObject("userId", userId);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 拒绝台账查询页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_1.page")
	public AbstractModelAndView iframe_1(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_1", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		
		filter.setRequest(request);
		QueryResult<LoanRefused> result=managerOtherInfoInputService.findLoanRefusedByFilter(filter);
		JRadPagedQueryResult<LoanRefused> pagedResult = new JRadPagedQueryResult<LoanRefused>(filter, result);
		mv.addObject("userId", userId);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	
	/**
	 * 拜访台账查询页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_4.page")
	public AbstractModelAndView iframe_4(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_4", request);
		
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		
		filter.setRequest(request);
		QueryResult<VisitRegistLedger> result = OtherMusidataInputService.findVisitRegistLedgerByFilter(filter);
		JRadPagedQueryResult<VisitRegistLedger> pagedResult = new JRadPagedQueryResult<VisitRegistLedger>(filter, result);
		mv.addObject("userId", userId);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 拜访台账增加页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_4_create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView iframe_4_create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_4_create", request);
		return mv;
	}

	
	/**
	 * 执行添加 拜访台账
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_4_insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap iframe_4_insert(@ModelAttribute VisitRegistLedgerForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				if(StringUtils.isNotEmpty(form.getId())) {//update
					VisitRegistLedger regt = form.createModel(VisitRegistLedger.class);
					OtherMusidataInputService.updateVisitRegistLedgerParameter(regt);
					returnMap.put(RECORD_ID, regt.getId());
					
				}else{
					VisitRegistLedger vreg = form.createModel(VisitRegistLedger.class);
					String id = OtherMusidataInputService.insertVisitRegistLedgerParameter(vreg);
					returnMap.put(RECORD_ID, id);
				}
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	
	/**
	 * 拜访台账修改页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_4_update.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView iframe_4_update(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_4_create", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			VisitRegistLedger vreg = OtherMusidataInputService.findVisitRegistLedgerParameterById(id);
			mv.addObject("vreg", vreg);
		}
		return mv;
	}
}
