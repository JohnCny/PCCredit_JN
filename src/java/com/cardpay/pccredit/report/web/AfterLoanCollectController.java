package com.cardpay.pccredit.report.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.ManagerSalaryForm;
import com.cardpay.pccredit.manager.model.TPerformanceParameters;
import com.cardpay.pccredit.manager.service.StatisticsAttentiveBalanceSynchScheduleService;
import com.cardpay.pccredit.postLoan.filter.PostLoanFilter;
import com.cardpay.pccredit.postLoan.model.MibusidataForm;
import com.cardpay.pccredit.postLoan.service.PostLoanService;
import com.cardpay.pccredit.report.filter.AccLoanCollectFilter;
import com.cardpay.pccredit.report.model.AccLoanCollectInfo;
import com.cardpay.pccredit.report.service.CustomerTransferFlowService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 贷款汇总查询（客户经理）
 * @author chinh
 *
 * 2014-11-27上午10:50:49
 */
@Controller
@RequestMapping("/report/afterLoancollect/*")
@JRadModule("report.afterLoancollect")
public class AfterLoanCollectController extends BaseController{
	
	@Autowired
	private CustomerTransferFlowService customerTransferFlowService;
	
	@Autowired
	private StatisticsAttentiveBalanceSynchScheduleService  attentiveBalanceSynchScheduleService;
	
	private static final Logger logger = Logger.getLogger(AfterLoanCollectController.class);
	/**
	 * 贷款汇总查询(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 * 新增用信客户:首次提款时间在选定时间段内的客户		  |新增用信余额:提款时间在选定时间段内 且在结束时间前未还清的借据
	 * 累计用信客户:结束时间前提过款的客户                                              |累计用信余额:结束时间前未还清的所有借据
	 * 新增授信客户:授信时间在选定时间段内的客户                                  |新增授信余额:授信时间在选定时间段内的合同,且结束日期前未结束的合同
	 * 累计授信客户:结束时间前授信的客户                                                  |累计授信总额:结束时间前授信的合同,且结束日期前未结束的合同
	 * 新增逾期客户数:逾期时间在选定时间段内的客户                              |新增逾期余额:逾期时间在选定时间段内的借据
	 * 累计逾期客户数:逾期时间在结束时间前的客户                                  |累计逾期余额:逾期时间在结束时间前的借据
	 * 用信余额（日均):(选定时间段内每天的用信余额求和)/时间段的天数
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "browseAll.page", method = { RequestMethod.GET })
	public AbstractModelAndView browseAll(@ModelAttribute AccLoanCollectFilter filter, HttpServletRequest request) throws ParseException {
		filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
        
        //如果当前客户是客户经理角色 userId is not null
        List<AccountManagerParameter> list = customerTransferFlowService.findManager(user.getId());
        if(list != null && list.size() > 0){
        	if(user.getUserType() ==1){//客户经理
        		filter.setUserId(user.getId());
        	}
		}
		
		if(StringUtils.isEmpty(filter.getStartDate())){
			filter.setStartDate("2016-05-01");
		}
		
		if(StringUtils.isEmpty(filter.getEndDate())){
			filter.setEndDate(DateHelper.getDateFormat(new Date(),"yyyy-MM-dd"));
		}
		
		long start = System.currentTimeMillis();
		List<AccLoanCollectInfo> accloanList = customerTransferFlowService.getAccLoanCollect(filter);
		//查询 pos流水贷产品的贷款余额
		BigDecimal pslsd = customerTransferFlowService.findSubListManagerByManagerId(user);
		for(AccLoanCollectInfo acc :accloanList){
			acc.setPos(pslsd.toString());
		}
		
		//查询客户经理count
		int count = customerTransferFlowService.findManagerListCount();
		
		//查询当月日均用信
		BigDecimal monthAverageAmt = customerTransferFlowService.findMonthAverageAmt();
		for(AccLoanCollectInfo acc :accloanList){
			acc.setMa(monthAverageAmt.divide(new BigDecimal(count),2,BigDecimal.ROUND_HALF_UP).toString());
		}
		
		//查询业务开展以来截止到如今的日均用信
		BigDecimal totalMonthAverageAmt = customerTransferFlowService.findToalMonthAverageAmt();
		int monthCount = returnMonthCount();
		BigDecimal monthManagerCount = new BigDecimal(monthCount).multiply(new BigDecimal(count));
		for(AccLoanCollectInfo acc :accloanList){
			acc.setTa(totalMonthAverageAmt.divide(monthManagerCount,2,BigDecimal.ROUND_HALF_UP).toString());
		}
		
		long end = System.currentTimeMillis();
		logger.info("#########################贷款汇总查询时间花费：" + (end - start) + "毫秒");
		JRadModelAndView mv = new JRadModelAndView("/report/afteraccloan/afterAccLoanCollect_centre_browseAll", request);
		mv.addObject("list", accloanList);
		mv.addObject("filter", filter);
		mv.addObject("urlType", user.getUserType());
		return mv;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "handle.json")
	public JRadReturnMap handle(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String month = RequestHelper.getStringValue(request, ID);
			attentiveBalanceSynchScheduleService.dosynchyxyeMethod(month);
			
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	/**
	 * 计算两个月中间相差多少月
	 * 2016-11-28
	 * 2016-07-01
	 * @throws ParseException 
	 */
	public int returnMonthCount() throws ParseException{
	
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   String str1 = "2016-07-01";//start 
	   String str2 = sdf.format(new Date());//end

	   Calendar bef = Calendar.getInstance();
	   Calendar aft = Calendar.getInstance();
	   bef.setTime(sdf.parse(str1));
	   aft.setTime(sdf.parse(str2));
      
	   int result=0;
	   result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
	   if (result == 0) {
	     result = 1;
	   }
	   return result;
	}
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "tzbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView tzbrowse(@ModelAttribute PostLoanFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/report/lx/lx_browse", request);
		mv.addObject(PAGED_RESULT, "");
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "exportData.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap exportData(PostLoanFilter filter,HttpServletRequest request,HttpServletResponse response) {
		JRadReturnMap returnMap = new JRadReturnMap();
		
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
				customerTransferFlowService.getExportWageData(filter,response);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	
	
	
	
	
	
	
}
