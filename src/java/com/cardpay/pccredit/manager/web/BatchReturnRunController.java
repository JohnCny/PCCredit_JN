package com.cardpay.pccredit.manager.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.filter.BatchRunFilter;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.manager.service.DailyReportScheduleService;
import com.cardpay.pccredit.manager.service.ManagerLevelAdjustmentService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：批处理重跑controller
 * @author 宋辰
 */
@Controller
@RequestMapping("/batch/returnrun/*")
@JRadModule("batch.returnrun")
public class BatchReturnRunController extends BaseController{
	
	@Autowired
	private ManagerLevelAdjustmentService managerLevelAdjustmentService;

	@Autowired
	DailyReportScheduleService dailyReportScheduleService;
	
	
	/**
	 * 查看失败批处理信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute BatchRunFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<BatchTask> result = managerLevelAdjustmentService.findBatchFilter(filter);
		JRadPagedQueryResult<BatchTask> pagedResult = new JRadPagedQueryResult<BatchTask>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/batchreturnrun/batch_return_run", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 执行 确认
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "handleAdjustmentLevel.json")
	public JRadReturnMap handleAdjustmentLevel(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = RequestHelper.getStringValue(request, ID);
			//User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			//String ids = id.split("@")[0];
			String batchCode = id.split("@")[1];
			
			//客户经理日报batch
			if(batchCode.equals("rb")){
				dailyReportScheduleService.insertWeekSchedule();
			}
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
}
