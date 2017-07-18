package com.cardpay.pccredit.manager.web;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.service.ManagerOtherInfoInputService;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.cardpay.pccredit.manager.service.OtherMusidataInputService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.IUser;
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
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
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
		boolean lock =false;
		if(userId.equals(user.getId())){
			lock=true;
		}
		mv.addObject("userId", userId);	
		mv.addObject("lock", lock);
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
		boolean lock =false;
		if(userId.equals(user.getId())){
			lock=true;
		}
		mv.addObject("userId", userId);	
		mv.addObject("lock", lock);
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
		
		filter.setUserId(userId);
		QueryResult<VisitRegistLedger> result = otherMusidataInputService.findVisitRegistLedgerByFilter(filter);
		JRadPagedQueryResult<VisitRegistLedger> pagedResult = new JRadPagedQueryResult<VisitRegistLedger>(filter, result);
		boolean lock =false;
		if(userId.equals(user.getId())){
			lock=true;
		}
		mv.addObject("userId", userId);	
		mv.addObject("lock", lock);	
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
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		VisitRegistLedger vreg = new VisitRegistLedger();
		vreg.setVisitId(user.getId());
		mv.addObject("vreg", vreg);
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
		standingBookFilter.setState("0");//非申请拒绝的
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
			//applyStandingBook.setManagerId(user.getId());
			applyStandingBook.setCreatedTime(new Date());
			String id = IDGenerator.generateID();
			applyStandingBook.setId(id);
			
			//
			applyStandingBook.setCustomerName(applyStandingBook.getCustomerName().split("_")[0]);//包括n.customerName+"_"+n.id
			applyStandingBook.setManagerId(applyStandingBook.getManagerName());
			SystemUser sys =  otherMusidataInputService.queryCustomer(applyStandingBook.getManagerName());
			applyStandingBook.setManagerName(sys.getDisplayName());
			
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
	
	
	
	@RequestMapping(value = "getManager.json",method = RequestMethod.GET)
	public void getManager(HttpServletRequest request,PrintWriter printWriter){
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			List<AccountManagerParameterForm> accountManagerParameterForms = managerBelongMapService.findSubManagerBelongMapByManagerId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(accountManagerParameterForms);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "getCustomer.json",method = RequestMethod.GET)
	public void getCustomer(HttpServletRequest request,PrintWriter printWriter){
		try {
			String userId = RequestHelper.getStringValue(request, ID);
			// 查询拜访客户列表 
			List<VisitRegistLedger> vreg = otherMusidataInputService.findVisitRegistLedgerParameterByVisitId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(vreg);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "getParameter.json",method = RequestMethod.GET)
	public void getParameter(HttpServletRequest request,PrintWriter printWriter){
		try {
			String paramId = RequestHelper.getStringValue(request, ID);
			// 查询拜访客户列表
			VisitRegistLedger vreg = otherMusidataInputService.findVisitRegistLedgerParameterById(paramId.split("_")[1]);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(vreg);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//=======================================申请拒绝台账=======================================================//
	/**
	 * 查询申请台帐
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_2.page")
	public AbstractModelAndView iframe_2_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_2", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("1");//申请拒绝的
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_2_update.page")
	public AbstractModelAndView iframe_2_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_2_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "iframe_2_update.json")
	public JRadReturnMap iframe_2_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			applyStandingBook.setState("1");//拒绝
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
	
	//=======================================申请拒绝台账=======================================================//
	
	
	//=======================================征信=======================================================//
	
	@ResponseBody
	@RequestMapping(value = "iframe_5.page")
	public AbstractModelAndView iframe_5_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_5", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("2");//申请征信
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_5_update.page")
	public AbstractModelAndView iframe_5_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_5_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_5_update.json")
	public JRadReturnMap iframe_5_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			applyStandingBook.setState("2");//征信
			applyStandingBook.setQueryDate(applyStandingBook.getQueryDate());
			applyStandingBook.setQueryReason(applyStandingBook.getQueryReason());
			applyStandingBook.setSignDate(applyStandingBook.getSignDate());
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
	
	//=======================================征信=======================================================//
	
	
	//=======================================征信拒绝=======================================================//
	@ResponseBody
	@RequestMapping(value = "iframe_6.page")
	public AbstractModelAndView iframe_6_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_6", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("3");//征信拒绝
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_6_update.page")
	public AbstractModelAndView iframe_6_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_6_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_6_update.json")
	public JRadReturnMap iframe_6_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			applyStandingBook.setState("3");//征信拒绝
			/*applyStandingBook.setQueryDate(applyStandingBook.getQueryDate());
			applyStandingBook.setQueryReason(applyStandingBook.getQueryReason());
			applyStandingBook.setSignDate(applyStandingBook.getSignDate());*/
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
	//=======================================征信拒绝=======================================================//
	
	//=======================================实调=======================================================//
	
	@ResponseBody
	@RequestMapping(value = "iframe_7.page")
	public AbstractModelAndView iframe_7_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_7", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("4");//征信通过实调
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_7_update.page")
	public AbstractModelAndView iframe_7_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_7_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_7_update.json")
	public JRadReturnMap iframe_7_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			applyStandingBook.setState("4");//征信通过实调
			applyStandingBook.setHostManager(applyStandingBook.getHostManager());
			applyStandingBook.setAssistManager(applyStandingBook.getAssistManager());
			applyStandingBook.setActualDate(applyStandingBook.getActualDate());
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
	
	//=======================================实调=======================================================//
	
	
	//=======================================报告=======================================================//
	@ResponseBody
	@RequestMapping(value = "iframe_8.page")
	public AbstractModelAndView iframe_8_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_8", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("5");//报告
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_8_update.page")
	public AbstractModelAndView iframe_8_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_8_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_8_update.json")
	public JRadReturnMap iframe_8_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			applyStandingBook.setState("5");//报告
			applyStandingBook.setTabulaTime(applyStandingBook.getTabulaTime());// 制表时间
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
	
	//=======================================报告=======================================================//
	
	
	//=======================================内审=======================================================//
	/**
	 * 内审这一环节的录入由内审人员录入
	 * 查询已经报告的申请
	 * @param standingBookFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_9.page")
	public AbstractModelAndView iframe_9_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_9", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("5");//报告
		//standingBookFilter.setManagerId(userId);
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_9_update.page")
	public AbstractModelAndView iframe_9_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_9_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_9_update.json")
	public JRadReturnMap iframe_9_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			// 判断是否是内审岗
			String message;
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user.getUserType()!=5){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			applyStandingBook.setState("6");//内审通过
			applyStandingBook.setInternalAuditDate(applyStandingBook.getInternalAuditDate());
			applyStandingBook.setInternalAuditor(applyStandingBook.getInternalAuditor());
			applyStandingBook.setInternalAuditProdType(applyStandingBook.getInternalAuditProdType());
			applyStandingBook.setInternalAuditAmt(applyStandingBook.getInternalAuditAmt());
			applyStandingBook.setAppInterest(applyStandingBook.getAppInterest());
			applyStandingBook.setAppPeriod(applyStandingBook.getAppPeriod());
			applyStandingBook.setAppRepayMethod(applyStandingBook.getAppRepayMethod());
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
	//=======================================内审=======================================================//
	
	
	//=======================================上会登记=======================================================//
	/**
	 * 上会登记由审贷人员录入
	 * 查询内审通过待登记上会的申请
	 * @param standingBookFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_10.page")
	public AbstractModelAndView iframe_10_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_10", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("6");//内审通过待登记上会
		//standingBookFilter.setManagerId(userId);
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_10_update.page")
	public AbstractModelAndView iframe_10_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_10_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_10_update.json")
	public JRadReturnMap iframe_10_update(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			// 判断是否是审批岗
			String message;
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user.getUserType()!=5){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			applyStandingBook.setState("7");//上会中
			applyStandingBook.setOneMeetDate(applyStandingBook.getOneMeetDate());
			applyStandingBook.setTwoMeetDate(applyStandingBook.getTwoMeetDate());
			applyStandingBook.setThreeMeetDate(applyStandingBook.getThreeMeetDate());
			applyStandingBook.setAuditUser(applyStandingBook.getAuditUser());
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
	
	
	//=======================================上会登记=======================================================//
	
	
	//=======================================上会=======================================================//
	/**
	 * 查询上会中的申请
	 * @param standingBookFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_11.page")
	public AbstractModelAndView iframe_11_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_11", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("7");//上会中
		//standingBookFilter.setManagerId(userId);
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_11_refuse_update.page")
	public AbstractModelAndView iframe_11_refuse_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_11_refuse_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_11_pass_update.page")
	public AbstractModelAndView iframe_11_pass_update(HttpServletRequest request) {  
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_11_pass_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_11_pass.json")
	public JRadReturnMap iframe_11_pass(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			// 判断是否是审批岗
			String message;
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user.getUserType()!=5){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			applyStandingBook.setState("8");//上会通过
			applyStandingBook.setApprovedMeetDate(applyStandingBook.getApprovedMeetDate());
			applyStandingBook.setApprovedMeetProdType(applyStandingBook.getApprovedMeetProdType());
			applyStandingBook.setApprovedAmt(applyStandingBook.getApprovedAmt());
			applyStandingBook.setApprovedLv(applyStandingBook.getApprovedLv());
			applyStandingBook.setApprovedPeriod(applyStandingBook.getApprovedPeriod());
			applyStandingBook.setApprovedRepayMethod(applyStandingBook.getApprovedRepayMethod());
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_11_refuse.json")
	public JRadReturnMap iframe_11_refuse(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			// 判断是否是审批岗
			String message;
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user.getUserType()!=5){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			applyStandingBook.setState("9");//上会拒绝
			applyStandingBook.setMeetRefuseReason(applyStandingBook.getMeetRefuseReason());
			applyStandingBook.setMeetRefuseDate(applyStandingBook.getMeetRefuseDate());
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
	
	/**
	 * 重新上会
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_11_update.json")
	public JRadReturnMap iframe_11_update(HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			// 判断是否是审批岗
			String message;
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			if(user.getUserType()!=5){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			String id = RequestHelper.getStringValue(request, ID);
			//6-内审通过待登记上会 
			managerPerformmanceService.updateApplyStandingBook(id);
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.put("mess", "提交成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}
		
		return returnMap;
	}
	//=======================================上会=======================================================//
	
	
	
	//=======================================审批=======================================================//
	@ResponseBody
	@RequestMapping(value = "iframe_12.page")
	public AbstractModelAndView iframe_12_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_12", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		if("00000547".equals(user.getExternalId())){
			standingBookFilter.setState("8");//上会通过待小微贷负责人审批
		}else if("00000548".equals(user.getExternalId())){
			standingBookFilter.setState("10");//上会通过待总经理审批
		}else if("00000549".equals(user.getExternalId())){
			standingBookFilter.setState("11");//上会通过待行长审批
		}
		
		String userId = request.getParameter("userId");
		
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		//standingBookFilter.setManagerId(userId);
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_12_update.page")
	public AbstractModelAndView iframe_12_update(HttpServletRequest request) {
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_12_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		mv.addObject("exterId",user.getExternalId());
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "iframe_12_pass.json")
	public JRadReturnMap iframe_12_pass(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		try {
			// 判断是否是审批岗
			String message;
			if(user.getUserType()!=2&&user.getUserType()!=3){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			if("00000547".equals(user.getExternalId())){
				applyStandingBook.setRemark1(applyStandingBook.getRemark1());
				if(!isContinue(applyStandingBook,applyStandingBook.getApprovedAmt(),"00000547")){
					applyStandingBook.setState("12");//待签约
				}else{
					applyStandingBook.setState("10");//上会通过待总经理审批
				}
			}else if("00000548".equals(user.getExternalId())){
				applyStandingBook.setRemark2(applyStandingBook.getRemark2());
				if(!isContinue(applyStandingBook,applyStandingBook.getApprovedAmt(),"00000548")){
					applyStandingBook.setState("12");//待签约
				}else{
					applyStandingBook.setState("11");//上会通过待行长审批
				}
			}else if("00000549".equals(user.getExternalId())){
				applyStandingBook.setState("12");//待签约
				applyStandingBook.setRemark3(applyStandingBook.getRemark3());
			}
			
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
	
	// 审批流程控制
	public boolean isContinue(ApplyStandingBookModel applyStandingBook,String exAmount,String auditType){
		//查询产品担保方式
		if("LNM00000000003".equals(applyStandingBook.getApprovedMeetProdType())){//信用
			if("00000547".equals(auditType)){
				if(Float.parseFloat(exAmount)<=100000){
					return false;
				}else{
					return true;
				}
			}
			if("00000548".equals(auditType)){
				if(Float.parseFloat(exAmount)<=200000){//&&Float.parseFloat(exAmount)>100000
					return false;
				}else{
					return true;
				}
			}
			
		}else if("LNM00000000004".equals(applyStandingBook.getApprovedMeetProdType())){//担保
			if("00000547".equals(auditType)){
				if(Float.parseFloat(exAmount)<=200000){
					return false;
				}else{
					return true;
				}
			}
			if("00000548".equals(auditType)){
				if(Float.parseFloat(exAmount)<=800000){//&&Float.parseFloat(exAmount)>200000
					return false;
				}else{
					return true;
				}
			}
		}else if("LNM00000000001".equals(applyStandingBook.getApprovedMeetProdType())){//抵押
			if("00000547".equals(auditType)){
				if(Float.parseFloat(exAmount)<=500000){
					return false;
				}else{
					return true;
				}
			}
			if("00000548".equals(auditType)){
				if(Float.parseFloat(exAmount)<=2000000){//&&Float.parseFloat(exAmount)>500000
					return false;
				}else{
					return true;
				}
			}
		}
		return true;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "iframe_12_refuse.json")
	public JRadReturnMap iframe_12_refuse(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		try {
			// 判断是否是审批岗
			String message;
			if(user.getUserType()!=2&&user.getUserType()!=3){
				message = "保存失败,当前用户非审批岗!";
				returnMap.put("mess", message);
				returnMap.put(JRadConstants.SUCCESS, false);
				return returnMap;
			}
			
			if("00000547".equals(user.getExternalId())){
				applyStandingBook.setRemark3(applyStandingBook.getRemark1());
			}else if("00000548".equals(user.getExternalId())){
				applyStandingBook.setRemark3(applyStandingBook.getRemark2());
			}else if("00000549".equals(user.getExternalId())){
				applyStandingBook.setRemark3(applyStandingBook.getRemark3());
			}
			
			applyStandingBook.setState("13");//审核审批拒绝
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
	
	//=======================================审批=======================================================//
	
	
	//=======================================签约=======================================================//
	@ResponseBody
	@RequestMapping(value = "iframe_13.page")
	public AbstractModelAndView iframe_13_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_13", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("12");//审批审核通过待签约
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
	
	
	@ResponseBody
	@RequestMapping(value = "iframe_13_update.page")
	public AbstractModelAndView iframe_13_update(HttpServletRequest request) {
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String id = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_13_update", request);
		mv.addObject("result",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "iframe_13_pass.json")
	public JRadReturnMap iframe_13_pass(@ModelAttribute ApplyStandingBookModel applyStandingBook,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			applyStandingBook.setState("14");//已签约 待放款
			applyStandingBook.setDateSign(applyStandingBook.getDateSign());
			applyStandingBook.setSignPerson(applyStandingBook.getSignPerson());
			applyStandingBook.setSignPlace(applyStandingBook.getSignPlace());
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
	//=======================================签约=======================================================//
	
	/**
	 * 查询已经签约待放款
	 * @param standingBookFilter
	 * @param request
	 * @return
	 */
	//=======================================放款=======================================================//
	@ResponseBody
	@RequestMapping(value = "iframe_14.page")
	public AbstractModelAndView iframe_14_browse(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_14", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		standingBookFilter.setState("14");//已签约待放款
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
	
	@ResponseBody
	@RequestMapping(value = "iframe_14_pass.page")
	public AbstractModelAndView iframe_14_pass(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_14_pass", request);
		String userId = request.getParameter("userId");
		String appId = request.getParameter("id");//申请id
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(appId);
		mv.addObject("userId", userId);
		mv.addObject("appModel",ApplyStandingBookModel.get(0));
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "iframe_14_pass.json", method = { RequestMethod.POST })
	public JRadReturnMap iframe_14_pass(@ModelAttribute LoanApproved loanApproved,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	
	
	@ResponseBody
	@RequestMapping(value = "iframe_14_refuse.page")
	public AbstractModelAndView iframe_14_refuse(@ModelAttribute DimensionalFilter filter,HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_14_refuse", request);
		String userId = request.getParameter("userId");
		String appId = request.getParameter("id");
		List<ApplyStandingBookModel> ApplyStandingBookModel =managerPerformmanceService.queryapplyStandingBook(appId);
		mv.addObject("userId", userId);
		mv.addObject("appModel",ApplyStandingBookModel.get(0));
		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 保存放款拒绝台账
	 * @param loanApproved
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "iframe_14_refuse.json", method = { RequestMethod.POST })
	public JRadReturnMap iframe_4_refuse(@ModelAttribute LoanRefused loanRefused,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	//=======================================放款=======================================================//
	
	
	//=======================================台帐进度查询=======================================================//
	
	@ResponseBody
	@RequestMapping(value = "iframe_query.page")
	public AbstractModelAndView iframe_query(@ModelAttribute StandingBookFilter standingBookFilter,HttpServletRequest request) { 
		standingBookFilter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/otherinfoinput/iframe_query", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			userId = user.getId();
		}
		if(user.getUserType()==1){
			standingBookFilter.setManagerId(userId);	
		}
		
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
	
	
	//=======================================台帐进度查询=======================================================//
	
}
