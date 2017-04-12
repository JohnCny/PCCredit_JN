package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceModel;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceSum;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.cardpay.pccredit.manager.web.ManagerPerformmanceController;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadManagerPerformmanceController {
	private static final Logger logger = Logger.getLogger(JnpadManagerPerformmanceController.class);
	@Autowired
	private ManagerPerformmanceService managerPerformmanceService;
	
	
	//录入前查询
	@ResponseBody
	@RequestMapping(value = "/ipad/performmance/insertSelect.json")
	@JRadOperation(JRadOperation.BROWSE)
	public String create(HttpServletRequest request) {        
		String userId = request.getParameter("userId");
		//统计每天申请拒绝数
		int refuseNum=managerPerformmanceService.queryRefuse(userId);
		//统计每天申请数
		int applyNum= managerPerformmanceService.queryApply(userId);
		//统计每天内审数
		int auditNum=managerPerformmanceService.queryAudit(userId);
		//统计每天上会数
		int willNum=managerPerformmanceService.queryWill(userId);
		//统计每天通过数
		int passNum=managerPerformmanceService.queryPass(userId);
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("applyNum", applyNum);
		map.put("refuseNum", refuseNum);
		map.put("auditNum", auditNum);
		map.put("willNum", willNum);
		map.put("passNum", passNum);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 执行录入
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/performmance/update.json")
	public String update(@ModelAttribute ManagerPerformmanceModel managerPerformmance,HttpServletRequest request) {        
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String userId = request.getParameter("userId");
		try {

		
			managerPerformmance.setManager_id(userId);
			ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(userId);
			if(managerPerformmanceold!=null){
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String date1=format.format(managerPerformmanceold.getCrateday());
//			String date2=format.format(new Date());
//			if(date1.equals(date2)){
				map.put(JRadConstants.SUCCESS, false);
				map.put("mess", "当天已经提交过，不能重复提交");
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
//			}
			}
			String id = IDGenerator.generateID();
			managerPerformmance.setId(id);
			managerPerformmance.setCrateday(new Date());
			managerPerformmanceService.insertmanagerPerformmance(managerPerformmance); 
			map.put("mess", "提交成功");
		} catch (Exception e) {
			map.put(JRadConstants.SUCCESS, false);
			map.put("mess", "提交失败");
		}

		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 客户经理业绩进度查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/performmance/browse.page")
	@JRadOperation(JRadOperation.BROWSE)
	public String browse(HttpServletRequest request) { 
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
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
		if(orgId==null||orgId==""){
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
		}
		 long end = System.currentTimeMillis();
		logger.info("查询时间花费：" + (end - start) + "毫秒");
		map.put("result", gxperformList);
		map.put("satrtDate", satrtDate);
		map.put("endDate", endDate);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}

	//执行修改进度
	@ResponseBody
	@RequestMapping(value = "/ipad/performmance/performUpdate.json")
	public String updateinfo(@ModelAttribute ManagerPerformmanceModel ManagerPerformmance,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		
		if(ManagerPerformmance.getManager_id().equals("0")){
			returnMap.put("mess", "请选择客户经理");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
			return json.toString();
		}
		try {
			String changedate = request.getParameter("changedate");
			if(changedate!=""){
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
			}else{
				returnMap.put("mess", "日期不能为空");
			}
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
	//根据ID查指定客户经理业绩进度总和
			@ResponseBody
			@RequestMapping(value = "/ipad/performmance/performselect.json")
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

			//查看转化率统计图
			@ResponseBody
			@RequestMapping(value = "/ipad/performmance/performMakeRates.json")
			public String performakeRates(HttpServletRequest request) {
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
				Map<String, String> map = new LinkedHashMap<String, String>();
				List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
				String bankList="";
				for (BankListForm bankListForm2 : bankListForm) {
					bankList += "<option value = '"+bankListForm2.getId()+"'>"+bankListForm2.getName()+"</option>";
				}
				map.put("classifiedJsonData",getClassifiedJson(managerPerformmanceForm));
				map.put("satrtDates", satrtDate);
				map.put("endDates", endDate);
				map.put("bankList", bankList);
				map.put("orgId", orgId);
				JSONObject json = JSONObject.fromObject(map);
				String jsons=json.toString();
				return jsons;
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
			@RequestMapping(value = "/ipad/performmance/performRanking.json")
			@JRadOperation(JRadOperation.BROWSE)
			public String performmancepaiming(HttpServletRequest request){
				String satrtDate = request.getParameter("startdate");
				String endDate = request.getParameter("enddate");
				String orgId = request.getParameter("orgId");
				String classes = request.getParameter("classes");
				if(classes==null||classes==""){
					classes="money";
				}
				List<ManagerPerformmance> gxperformList = managerPerformmanceService.findSumPerformmanceRanking(orgId,satrtDate,endDate,classes);
				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
				List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
				String bankList="";
				for (BankListForm bankListForm2 : bankListForm) {
					bankList += "<option value = '"+bankListForm2.getId()+"'>"+bankListForm2.getName()+"</option>";
				}
				map.put("bankList", bankList);
				map.put("orgId", orgId);
				map.put("classes", classes);
				map.put("result", gxperformList);
				map.put("satrtDate", satrtDate);
				map.put("endDate", endDate);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
				
			}
}