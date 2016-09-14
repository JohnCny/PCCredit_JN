package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.cardpay.pccredit.manager.model.ManagerPerformmanceSum;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadManagerPerformmanceController {
	
	@Autowired
	private ManagerPerformmanceService managerPerformmanceService;
	
	/**
	 * 执行录入
	 * @param managerPerformmance
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/performmance/update.json")
	public String update(@ModelAttribute ManagerPerformmance managerPerformmance,HttpServletRequest request) {        
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			
			String userId = request.getParameter("userId");
			managerPerformmance.setManager_id(userId);
			ManagerPerformmanceSum managerPerformmanceSum = managerPerformmanceService.finManagerPerformmanceSumById(userId);
			
			
			ManagerPerformmanceSum ManagerPerformmanceSum =new ManagerPerformmanceSum();
			String id = IDGenerator.generateID();
			ManagerPerformmanceSum.setId(id);
			ManagerPerformmanceSum.setCrateday_s(new Date());
			ManagerPerformmanceSum.setApplycount_s(managerPerformmance.getApplycount());
			ManagerPerformmanceSum.setApplyrefuse_s(managerPerformmance.getApplyrefuse());
			ManagerPerformmanceSum.setCreditcount_s(managerPerformmance.getCreditcount());
			ManagerPerformmanceSum.setCreditrefuse_s(managerPerformmance.getCreditrefuse());
			ManagerPerformmanceSum.setGivemoneycount_s(managerPerformmance.getGivemoneycount());
			ManagerPerformmanceSum.setInternalcount_s(managerPerformmance.getInternalcount());
			ManagerPerformmanceSum.setMeetingcout_s(managerPerformmance.getMeetingcout());
			ManagerPerformmanceSum.setPasscount_s(managerPerformmance.getPasscount());
			ManagerPerformmanceSum.setRealycount_s(managerPerformmance.getRealycount());
			ManagerPerformmanceSum.setReportcount_s(managerPerformmance.getReportcount());
			ManagerPerformmanceSum.setSigncount_s(managerPerformmance.getSigncount());
			ManagerPerformmanceSum.setVisitcount_s(managerPerformmance.getVisitcount());
			ManagerPerformmanceSum.setManager_id_s(managerPerformmance.getManager_id());
			
			if(managerPerformmanceSum==null){
				managerPerformmanceService.insertManagerPerformmanceSum(ManagerPerformmanceSum);
				
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date1=format.format(managerPerformmanceSum.getCrateday_s());
				String date2=format.format(new Date());
				if(date1.equals(date2)){
					map.put(JRadConstants.SUCCESS, false);
					map.put("mess", "当天已经提交过，不能重复提交");
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(map, jsonConfig);
					return json.toString();
				}
				ManagerPerformmanceSum.setId(managerPerformmanceSum.getId());
				managerPerformmanceService.updateManagerPerformmanceSum(ManagerPerformmanceSum);
				
				
			}
			ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(userId);
			if(managerPerformmanceold==null){
				managerPerformmanceService.insertmanagerPerformmance(managerPerformmance); 
				
			}else{
				managerPerformmance.setId(managerPerformmanceold.getId());
				managerPerformmanceService.updatemanagerPerformmance(managerPerformmance); 
			
			}
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
		List<BankListForm> bankListForm = managerPerformmanceService.findALlbank();
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		List<ManagerPerformmanceForm> gxperformList = new ArrayList<ManagerPerformmanceForm>();
		for(int j=0;j<bankListForm.size();j++){
			String id= bankListForm.get(j).getId();
		List<DeptMemberForm> gxMumberList = managerPerformmanceService.findMumberByDeptId(id);
		for(int i=0;i<gxMumberList.size();i++){
		String managerId =gxMumberList.get(i).getId();
		ManagerPerformmanceForm managerPerformmanceForm= managerPerformmanceService.findSumPerformmanceById(managerId);
		if(managerPerformmanceForm==null){
			continue;
		}
		managerPerformmanceForm.setName(gxMumberList.get(i).getOname());
		managerPerformmanceForm.setManagerName(gxMumberList.get(i).getDisplay_name());
		gxperformList.add(managerPerformmanceForm);
		}
		ManagerPerformmanceForm managerPerformmanceForm1 = managerPerformmanceService.findDeptSumPerformmanceById(id);
		if(managerPerformmanceForm1==null){
			continue;
		}
		managerPerformmanceForm1.setName(bankListForm.get(j).getName());
		managerPerformmanceForm1.setManagerName("小计");
		gxperformList.add(managerPerformmanceForm1);
		}
		ManagerPerformmanceForm managerPerformmanceForm2 = managerPerformmanceService.findALLDeptSumPerformmanceById();
		managerPerformmanceForm2.setName("统计");
		managerPerformmanceForm2.setManagerName("总计");
		gxperformList.add(managerPerformmanceForm2);
		
		map.put("result", gxperformList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}

	//执行修改进度
	@ResponseBody
	@RequestMapping(value = "/ipad/performmance/performUpdate.json")
	public String updateinfo(@ModelAttribute ManagerPerformmanceSum managerPerformmanceSum,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		if(managerPerformmanceSum.getManager_id_s().equals("0")){
			returnMap.put("mess", "请选择客户经理");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
			return json.toString();
		}
		try {
			ManagerPerformmanceSum managerPerformmanceSum2 = managerPerformmanceService.finManagerPerformmanceSumById(managerPerformmanceSum.getManager_id_s());
		
			if(managerPerformmanceSum2==null){
				managerPerformmanceService.insertManagerPerformmanceSum(managerPerformmanceSum);
				
			}else{
				
//				ManagerPerformmanceSum.setId(managerPerformmanceSum.getId());
				managerPerformmanceService.updateManagerPerformmanceSumInfor(managerPerformmanceSum);
				
				
			}
			returnMap.put("mess", "提交成功");
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
}
