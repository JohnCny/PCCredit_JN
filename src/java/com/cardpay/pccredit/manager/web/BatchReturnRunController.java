package com.cardpay.pccredit.manager.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.ReadWholeAndIncrementService;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.web.LocalExcelForm;
import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.filter.BatchRunFilter;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.manager.service.DailyReportScheduleService;
import com.cardpay.pccredit.manager.service.ManagerLevelAdjustmentService;
import com.cardpay.pccredit.toolsjn.OdsTools;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
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
	
	@Autowired
	CustomerInforService customerInforService;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	ReadWholeAndIncrementService incrementService;
	
	@Autowired
	OdsTools  odsTools;
	/**
	 * 查看批处理信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute BatchRunFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		//
		if(filter.getStartDate() ==null){
			filter.setStartDate(DateHelper.getDateFormat("2016-04-01","yyyy-MM-dd"));
		}
		if(filter.getEndDate()==null){
			filter.setEndDate(new Date());
		}
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
			String param = RequestHelper.getStringValue(request, ID);
			String batchCode = param.split("@")[1];
			String time = param.split("@")[2];
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dateString = format.format(new Date(time));
			
			//下载和解压数据
			if(batchCode.equals("downLoad")){
				odsTools.downloadFilesbyDate(dateString);
			//客户经理日报batch
			}else if(batchCode.equals("rb")){
				dailyReportScheduleService.insertWeekScheduleByDate(dateString);
			//ODS增量数据
			}else if(batchCode.equals("incre")){
				incrementService.doReadFileIncrementByDate(dateString);
			//ODS全量数据
			}else if(batchCode.equals("whole")){
				incrementService.doReadFileWholeByDate(dateString);
			}
			//同步进件状态
			//绩效
			
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "reportImport.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView reportImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/manager/batchreturnrun/batch_import",request);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "reportImport.json")
	public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			addIntoPiecesService.importTxt(file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, "导入成功");
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "导入失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
}
