package com.cardpay.pccredit.manager.web;

import java.io.IOException;
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
import com.cardpay.pccredit.intopieces.constant.CardStatus;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.main.MainController;
import com.cardpay.pccredit.manager.filter.StandingBookFilter;
import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.form.VisitRegistLedgerForm;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.ApplyStandingBookModel;
import com.cardpay.pccredit.manager.model.LoanApproved;
import com.cardpay.pccredit.manager.model.LoanRefused;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceModel;
import com.cardpay.pccredit.manager.model.VisitRegistLedger;
import com.cardpay.pccredit.manager.service.ManagerOtherInfoInputService;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.cardpay.pccredit.manager.service.OtherMusidataInputService;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
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
	private OtherMusidataInputService otherMusidataInputService;

	@Autowired
	private ManagerPerformmanceService managerPerformmanceService;
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
		filter.setUserId(userId);
		QueryResult<LoanApproved> result = managerOtherInfoInputService.findLoanApprovedByFilter(filter);
		JRadPagedQueryResult<LoanApproved> pagedResult = new JRadPagedQueryResult<LoanApproved>(filter, result);
		mv.addObject("userId", userId);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 跳转到放款台账新增界面
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_0_create.page")
	public AbstractModelAndView iframe_0_create(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_0_create", request);
		String userId = request.getParameter("userId");

		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 保存放款台账
	 * @param loanApproved
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_0_create.json", method = { RequestMethod.POST })
	public JRadReturnMap iframe_0_create_json(@ModelAttribute LoanApproved loanApproved,HttpServletRequest request, HttpServletResponse response) throws IOException {
		JRadReturnMap returnMap = new JRadReturnMap();
		loanApproved.setCreatedTime(new Date());
		loanApproved.setCreatedBy(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		
		try {
			managerOtherInfoInputService.insertLoanApproved(loanApproved);
			returnMap.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage(Beans.get(I18nHelper.class).getMessageNotNull(Constant.FAIL_MESSAGE));
		}

		return returnMap;
	}
	
	/**
	 * 跳转到放款台账修改界面
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_0_update.page")
	public AbstractModelAndView iframe_0_update(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_0_update", request);
		String id = request.getParameter("id");
		LoanApproved loanApproved = managerOtherInfoInputService.findLoanApprovedById(id);
		mv.addObject("obj", loanApproved);

		String userId = request.getParameter("userId");

		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 修改放款台账
	 * @param loanApproved
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_0_update.json", method = { RequestMethod.POST })
	public JRadReturnMap iframe_0_update_json(@ModelAttribute LoanApproved loanApproved,HttpServletRequest request, HttpServletResponse response) throws IOException {
		JRadReturnMap returnMap = new JRadReturnMap();
		loanApproved.setModifiedTime(new Date());
		loanApproved.setModifiedBy(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		
		try {
			managerOtherInfoInputService.updateLoanApproved(loanApproved);
			returnMap.setSuccess(true);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage(Beans.get(I18nHelper.class).getMessageNotNull(Constant.FAIL_MESSAGE));
		}
		
		return returnMap;
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
		filter.setUserId(userId);
		QueryResult<LoanRefused> result=managerOtherInfoInputService.findLoanRefusedByFilter(filter);
		JRadPagedQueryResult<LoanRefused> pagedResult = new JRadPagedQueryResult<LoanRefused>(filter, result);
		mv.addObject("userId", userId);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 跳转到放款台账新增界面
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_1_create.page")
	public AbstractModelAndView iframe_1_create(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_1_create", request);
		String userId = request.getParameter("userId");

		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 保存放款台账
	 * @param loanApproved
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_1_create.json", method = { RequestMethod.POST })
	public JRadReturnMap iframe_1_create_json(@ModelAttribute LoanRefused loanRefused,HttpServletRequest request, HttpServletResponse response) throws IOException {
		JRadReturnMap returnMap = new JRadReturnMap();
		loanRefused.setCreatedTime(new Date());
		loanRefused.setCreatedBy(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		
		try {
			managerOtherInfoInputService.insertLoanRefused(loanRefused);
			returnMap.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage(Beans.get(I18nHelper.class).getMessageNotNull(Constant.FAIL_MESSAGE));
		}

		return returnMap;
	}
	
	/**
	 * 跳转到放款台账修改界面
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_1_update.page")
	public AbstractModelAndView iframe_1_update(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_1_update", request);
		String id = request.getParameter("id");
		LoanRefused loanRefused = managerOtherInfoInputService.findLoanRefusedById(id);
		mv.addObject("obj", loanRefused);

		String userId = request.getParameter("userId");

		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 修改放款台账
	 * @param loanApproved
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_1_update.json", method = { RequestMethod.POST })
	public JRadReturnMap iframe_1_update_json(@ModelAttribute LoanRefused loanRefused,HttpServletRequest request, HttpServletResponse response) throws IOException {
		JRadReturnMap returnMap = new JRadReturnMap();
		loanRefused.setModifiedTime(new Date());
		loanRefused.setModifiedBy(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		
		try {
			managerOtherInfoInputService.updateLoanRefused(loanRefused);
			returnMap.setSuccess(true);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			returnMap.setSuccess(false);
			returnMap.addGlobalMessage(Beans.get(I18nHelper.class).getMessageNotNull(Constant.FAIL_MESSAGE));
		}
		
		return returnMap;
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
		QueryResult<VisitRegistLedger> result = otherMusidataInputService.findVisitRegistLedgerByFilter(filter);
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
			SystemUser sys =  otherMusidataInputService.queryCustomer(form.getVisitId());
			try {
				if(StringUtils.isNotEmpty(form.getId())) {//update
					VisitRegistLedger regt = form.createModel(VisitRegistLedger.class);
					if(sys!=null){
						regt.setVisitManager(sys.getDisplayName());
					}
					otherMusidataInputService.updateVisitRegistLedgerParameter(regt);
					returnMap.put(RECORD_ID, regt.getId());
					
				}else{
					VisitRegistLedger vreg = form.createModel(VisitRegistLedger.class);
					if(sys!=null){
						vreg.setVisitManager(sys.getDisplayName());
					}
					String id = otherMusidataInputService.insertVisitRegistLedgerParameter(vreg);
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
			VisitRegistLedger vreg = otherMusidataInputService.findVisitRegistLedgerParameterById(id);
			mv.addObject("vreg", vreg);
		}
		return mv;
	}
	
	/**
	 * 查询申请台帐
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_3.page")
	public AbstractModelAndView ApplyBookbrowse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_3", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setManagerId(userId);
		QueryResult<ApplyStandingBookModel> applyStandingBookModel=managerPerformmanceService.findApplyStandingBookByFilter(standingBookFilter);
		JRadPagedQueryResult<ApplyStandingBookModel> pagedResult = new JRadPagedQueryResult<ApplyStandingBookModel>(standingBookFilter, applyStandingBookModel);
		boolean lock =false;
		if(userId.equals(user.getId())){
			lock=true;
		}
		mv.addObject("userId", userId);	
		mv.addObject("lock", lock);	
		mv.addObject("filter", standingBookFilter);	
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 申请台帐录入页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "applyinsert.page")
	public AbstractModelAndView create_apply(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/applyStandingBookCreated", request);
		return mv;
	}
	
	/**
	 * 申请台帐执行录入
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateApply.json")
	public JRadReturnMap updateApply(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {

			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			applyStandingBook.setManagerId(user.getId());
			applyStandingBook.setCreatedTime(new Date());
			String id = IDGenerator.generateID();
			applyStandingBook.setId(id);
			managerPerformmanceService.insertapplyStandingBook(applyStandingBook); 
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
	 * 申请台帐执行删除
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "applydelete.json")
	public JRadReturnMap deleteApply(HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = request.getParameter("id");
			managerPerformmanceService.deleteapplyStandingBook(id); 
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
	 * 申请台帐更新页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "applyichange.page")
	public AbstractModelAndView change_apply(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/applyStandingBookChange", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	/**
	 * 申请台帐执行更新
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "applychange.json")
	public JRadReturnMap changeApply(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			managerPerformmanceService.changeapplyStandingBook(applyStandingBook); 
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.put("mess", "提交成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}
		
		return returnMap;
	}
}
