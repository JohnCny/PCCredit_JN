package com.cardpay.pccredit.manager.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.main.MainController;
import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceModel;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/manager/performmance/*")
@JRadModule("manager.performmance")
public class ManagerPerformmanceController extends BaseController {
	private static final Logger logger = Logger.getLogger(ManagerPerformmanceController.class);
	@Autowired
	private ManagerPerformmanceService managerPerformmanceService;

	/**
	 * 业绩录入页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmanceInsert", request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		
		//统计每天申请拒绝数
		int refuseNum=managerPerformmanceService.queryRefuse(user.getId());
		//统计每天申请数
		int applyNum= managerPerformmanceService.queryApply(user.getId());
		//统计每天内审数
		int auditNum=managerPerformmanceService.queryAudit(user.getId());
		//统计每天上会数
		int willNum=managerPerformmanceService.queryWill(user.getId());
		//统计每天通过数
		int passNum=managerPerformmanceService.queryPass(user.getId());
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("applyNum", applyNum);
		map.put("refuseNum", refuseNum);
		map.put("auditNum", auditNum);
		map.put("willNum", willNum);
		map.put("passNum", passNum);
		mv.addObject("ssss", map);
		return mv;
	}
	/**
	 * 客户经理业绩进度查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page")
	public AbstractModelAndView browse(HttpServletRequest request) { 
		List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
		List<ManagerPerformmanceForm> gxperformList = new ArrayList<ManagerPerformmanceForm>();
		String satrtDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		String orgId = request.getParameter("orgId");
		if(satrtDate!=null&&satrtDate!=""){
			satrtDate+=" 00:00:00";
		}
		if(endDate!=null&&endDate!=""){
			endDate+=" 23:59:59";
		}
		long start = System.currentTimeMillis();
		if(orgId==null||orgId==""||orgId.equals("000000")){
			for (BankListForm bankListForms : bankListForm) {
				String id = bankListForms.getId();
				List<ManagerPerformmanceForm> managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(id,satrtDate,endDate);
				for (ManagerPerformmanceForm managerPerformmanceForm2 : managerPerformmanceForm) {
					managerPerformmanceForm2.setName(bankListForms.getName());
				}
				gxperformList.addAll(managerPerformmanceForm);

			}
			
		ManagerPerformmanceForm managerPerformmanceForm2 = managerPerformmanceService.findALLDeptSumPerformmanceById(satrtDate,endDate);
			managerPerformmanceForm2.setName("统计");
			managerPerformmanceForm2.setManagerName("总计");
			gxperformList.add(managerPerformmanceForm2);
		}else{
			String name=managerPerformmanceService.getOrgName(orgId);
			List<ManagerPerformmanceForm> managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(orgId,satrtDate,endDate);
			for (ManagerPerformmanceForm managerPerformmanceForm2 : managerPerformmanceForm) {
				managerPerformmanceForm2.setName(name);
			}
			gxperformList.addAll(managerPerformmanceForm);
		}
		 long end = System.currentTimeMillis();
		logger.info("查询时间花费：" + (end - start) + "毫秒");
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmance_sum", request);
		mv.addObject("gxperformList", gxperformList);
		mv.addObject("satrtDate", satrtDate);
		mv.addObject("endDate", endDate);
		mv.addObject("orgId", orgId);
		return mv;
	}

	/**
	 * 执行录入
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	public JRadReturnMap update(@ModelAttribute ManagerPerformmanceModel managerPerformmance,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {

			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			managerPerformmance.setManager_id(user.getId());
			ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(user.getId());
			if(managerPerformmanceold!=null){
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String date1=format.format(managerPerformmanceold.getCrateday());
//			String date2=format.format(new Date());
//			if(date1.equals(date2)){
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put("mess", "当天已经提交过，不能重复提交");
				return returnMap;
//			}
			}
			String id = IDGenerator.generateID();
			managerPerformmance.setId(id);
			managerPerformmance.setCrateday(new Date());
			managerPerformmanceService.insertmanagerPerformmance(managerPerformmance); 
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.put("mess", "提交成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}

		return returnMap;
	}

	//修改进度页面
	@ResponseBody
	@RequestMapping(value = "performUpdate.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView performUpdate(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmanceUpdate", request);
		return mv;
	}
	//根据ID查指定客户经理业绩进度总和
		@ResponseBody
		@RequestMapping(value = "performsumselect.json")
		@JRadOperation(JRadOperation.BROWSE)
		public String performselect(HttpServletRequest request) {        
			
			String managerId= request.getParameter("managerId");
			String changedate = request.getParameter("changedate");
			ManagerPerformmance managerperformmance= managerPerformmanceService.finManagerPerformmanceSumById(managerId,changedate);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(managerperformmance, jsonConfig);
			return json.toString();
		}

	//导出业绩进度信息
	@ResponseBody
	@RequestMapping(value = "performexport.json", method = { RequestMethod.GET })
	public JRadReturnMap JRadReturnMap(HttpServletRequest request,HttpServletResponse response) {        

		JRadReturnMap returnMap = new JRadReturnMap();
		String satrtDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		String orgId = request.getParameter("orgId");
		String satrtDate1 = satrtDate;
		String endDate1 = endDate;
		try{
			List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
			List<ManagerPerformmanceForm> gxperformList = new ArrayList<ManagerPerformmanceForm>();
			if(satrtDate!=null&&satrtDate!=""){
				satrtDate+=" 00:00:00";
			}
			if(endDate!=null&&endDate!=""){
				endDate+=" 23:59:59";
			}
			long start = System.currentTimeMillis();
			if(orgId==null||orgId==""||orgId.equals("000000")){
				for (BankListForm bankListForms : bankListForm) {
					String id = bankListForms.getId();
					List<ManagerPerformmanceForm> managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(id,satrtDate,endDate);
					for (ManagerPerformmanceForm managerPerformmanceForm2 : managerPerformmanceForm) {
						managerPerformmanceForm2.setName(bankListForms.getName());
					}
					gxperformList.addAll(managerPerformmanceForm);

				}
				
			ManagerPerformmanceForm managerPerformmanceForm2 = managerPerformmanceService.findALLDeptSumPerformmanceById(satrtDate,endDate);
				managerPerformmanceForm2.setName("统计");
				managerPerformmanceForm2.setManagerName("总计");
				gxperformList.add(managerPerformmanceForm2);
			}else{
				String name=managerPerformmanceService.getOrgName(orgId);
				List<ManagerPerformmanceForm> managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(orgId,satrtDate,endDate);
				for (ManagerPerformmanceForm managerPerformmanceForm2 : managerPerformmanceForm) {
					managerPerformmanceForm2.setName(name);
				}
				gxperformList.addAll(managerPerformmanceForm);
			}
			 long end = System.currentTimeMillis();
			 logger.info("查询时间花费：" + (end - start) + "毫秒");
			managerPerformmanceService.getExportWageData(gxperformList, response,satrtDate1,endDate1);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}


	//	//执行修改进度
		@ResponseBody
		@RequestMapping(value = "performUpdate.json")
		public JRadReturnMap updateinfo(@ModelAttribute ManagerPerformmanceModel ManagerPerformmance,HttpServletRequest request) {        
			JRadReturnMap returnMap = new JRadReturnMap();
	
			if(ManagerPerformmance.getManager_id().equals("0")){
				returnMap.put("mess", "请选择客户经理");
				return returnMap;
			}
			try {
				String changedate = request.getParameter("changedate");
				ManagerPerformmance managerperformmance= managerPerformmanceService.finManagerPerformmanceSumById(ManagerPerformmance.getManager_id(),changedate);
				if(managerperformmance==null){
					String id = IDGenerator.generateID();
					ManagerPerformmance.setId(id);
					String oldDate=changedate+" 12:00:00";
					ManagerPerformmance.setCrateday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate));
					managerPerformmanceService.insertmanagerPerformmance(ManagerPerformmance);
					returnMap.put("mess", "该客户经理指定日期进度不存在，已补录成功！");
				}else{
					
					ManagerPerformmance.setId(managerperformmance.getId());
					managerPerformmanceService.updateManagerPerformmanceSumInfor(ManagerPerformmance);
					
					returnMap.put("mess", "修改进度成功");
				}
			} catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put("mess", "提交失败");
				returnMap.addGlobalMessage("保存失败");
			}
			return returnMap;
		}


		//查看转化率统计图
		@ResponseBody
		@RequestMapping(value = "performMakeRates.page")
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView performakeRates(HttpServletRequest request) {
			String satrtDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
			String orgId=request.getParameter("orgId");
			ManagerPerformmanceForm managerPerformmanceForm;
			if(orgId!=null&&orgId!=""){
				if(orgId.equals("000000")){
					managerPerformmanceForm= managerPerformmanceService.findALLDeptSumPerformmanceById(satrtDate,endDate);
				}else{
				managerPerformmanceForm = managerPerformmanceService.findDeptSumPerformmanceById(orgId,satrtDate,endDate);	
				}
				}else{
				managerPerformmanceForm= managerPerformmanceService.findALLDeptSumPerformmanceById(satrtDate,endDate);
			}
			JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmancemakeRates", request);
			mv.addObject("classifiedJsonData",getClassifiedJson(managerPerformmanceForm));
			mv.addObject("satrtDates", satrtDate);
			mv.addObject("endDates", endDate);
			mv.addObject("orgId", orgId);
			return mv;
		}
		public String getClassifiedJson(ManagerPerformmanceForm managerPerformmanceForm){
			ArrayList<Object> datajson= new ArrayList<Object>();
			if(managerPerformmanceForm!=null){
			//拜访到申请转化率
			double shenqing=0;
			if(managerPerformmanceForm.getVisitcount_s()>0){
				shenqing=((double)managerPerformmanceForm.getApplycount_s()/(double)managerPerformmanceForm.getVisitcount_s())*100;
			}
			datajson.add(shenqing);
			//申请到内审转化率
			double neishen=0;
			if(managerPerformmanceForm.getApplycount_s()>0){
			neishen=((double)managerPerformmanceForm.getInternalcount_s()/(double)managerPerformmanceForm.getApplycount_s())*100;
			}
			datajson.add(neishen);
			//内审到上会转化率
			double shanghui=0;
			if(managerPerformmanceForm.getInternalcount_s()>0){
				 shanghui =((double)managerPerformmanceForm.getMeetingcout_s()/(double)managerPerformmanceForm.getInternalcount_s())*100;
			}
			datajson.add(shanghui);
			//过会比率
			double guohui=0;
			if(managerPerformmanceForm.getMeetingcout_s()>0){
				guohui =((double)managerPerformmanceForm.getPasscount_s()/(double)managerPerformmanceForm.getMeetingcout_s())*100;
			}
			datajson.add(guohui);
			//放款比率
			double fangkuan=0;
			if(managerPerformmanceForm.getPasscount_s()>0){
			fangkuan =((double)managerPerformmanceForm.getGivemoneycount_s()/(double)managerPerformmanceForm.getPasscount_s())*100;
			}
			datajson.add(fangkuan);
			}else{
				datajson.add(0);
				datajson.add(0);
				datajson.add(0);
				datajson.add(0);
				datajson.add(0);
			}
			return JSONArray.fromObject(datajson).toString();
		}
		
		//业绩进度排名
		@ResponseBody
		@RequestMapping(value = "performRanking.page")
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView performmancepaiming(HttpServletRequest request){
			String satrtDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
			String orgId = request.getParameter("orgId");
			String classes = request.getParameter("classes");
			if(classes==null||classes==""){
				classes="money";
			}
			List<ManagerPerformmance> gxperformList = managerPerformmanceService.findSumPerformmanceRanking(orgId,satrtDate,endDate,classes);
			JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmanceRankings", request);
			mv.addObject("gxperformList", gxperformList);
			mv.addObject("satrtDate", satrtDate);
			mv.addObject("endDate", endDate);
			mv.addObject("orgId", orgId);
			mv.addObject("classes", classes);
			return mv;
			
		}
		
		//导出排名
		@ResponseBody
		@RequestMapping(value = "performmanceRanking.json", method = { RequestMethod.GET })
		public JRadReturnMap performmanceRanking(HttpServletRequest request,HttpServletResponse response) { 
			JRadReturnMap returnMap = new JRadReturnMap();
			String satrtDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
			String orgId = request.getParameter("orgId");
			String classes = request.getParameter("classes");
			if(classes==null||classes==""){
				classes="money";
			}
			List<ManagerPerformmance> gxperformList = managerPerformmanceService.findSumPerformmanceRanking(orgId,satrtDate,endDate,classes);
			try {
				
				managerPerformmanceService.getRankingExport(gxperformList, response, satrtDate, endDate);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
			
			return returnMap;
			
		}
}
