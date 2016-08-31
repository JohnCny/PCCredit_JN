package com.cardpay.pccredit.manager.web;

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

import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceSum;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/manager/performmance/*")
@JRadModule("manager.performmance")
public class ManagerPerformmanceController extends BaseController {

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
		return mv;
	}
	/**
	 * 客户经理业绩进度查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {  
		String id ="ff8080815456203101545635015f0011";
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		List<DeptMemberForm> gxMumberList = managerPerformmanceService.findMumberByDeptId(id);
		List<ManagerPerformmanceForm> gxperformList = new ArrayList<ManagerPerformmanceForm>();
		for(int i=0;i<gxMumberList.size();i++){
		String managerId =gxMumberList.get(i).getId();
		ManagerPerformmance managerPerformmance = managerPerformmanceService.finManagerPerformmanceById(managerId);
		ManagerPerformmanceSum managerPerformmanceSum = managerPerformmanceService.finManagerPerformmanceSumById(managerId);
		ManagerPerformmanceForm managerPerformmanceForm =new ManagerPerformmanceForm();
		managerPerformmanceForm.setApplycount(managerPerformmanceSum.getApplycount_s()+"("+managerPerformmance.getApplycount()+")");
		managerPerformmanceForm.setApplyrefuse(managerPerformmanceSum.getApplyrefuse_s()+"("+managerPerformmance.getApplyrefuse()+")");
		managerPerformmanceForm.setCreditcount(managerPerformmanceSum.getCreditcount_s()+"("+managerPerformmance.getCreditcount()+")");
		managerPerformmanceForm.setCreditrefuse(managerPerformmanceSum.getCreditrefuse_s()+"("+managerPerformmance.getCreditrefuse()+")");
		managerPerformmanceForm.setGivemoneycount(managerPerformmanceSum.getGivemoneycount_s()+"("+managerPerformmance.getGivemoneycount()+")");
		managerPerformmanceForm.setInternalcount(managerPerformmanceSum.getInternalcount_s()+"("+managerPerformmance.getInternalcount()+")");
		managerPerformmanceForm.setMeetingcout(managerPerformmanceSum.getMeetingcout_s()+"("+managerPerformmance.getMeetingcout()+")");
		managerPerformmanceForm.setPasscount(managerPerformmanceSum.getPasscount_s()+"("+managerPerformmance.getPasscount()+")");
		managerPerformmanceForm.setRealycount(managerPerformmanceSum.getRealycount_s()+"("+managerPerformmance.getRealycount()+")");
		managerPerformmanceForm.setReportcount(managerPerformmanceSum.getReportcount_s()+"("+managerPerformmance.getReportcount()+")");
		managerPerformmanceForm.setSigncount(managerPerformmanceSum.getSigncount_s()+"("+managerPerformmance.getSigncount()+")");
		managerPerformmanceForm.setVisitcount(managerPerformmanceSum.getVisitcount_s()+"("+managerPerformmance.getVisitcount()+")");
		managerPerformmanceForm.setName(gxMumberList.get(i).getOname());
		managerPerformmanceForm.setManagerName(gxMumberList.get(i).getDisplay_name());
		gxperformList.add(managerPerformmanceForm);
		}
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmance_sum", request);
		mv.addObject("gxperformList", gxperformList);
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
	public JRadReturnMap update(@ModelAttribute ManagerPerformmance managerPerformmance,HttpServletRequest request) {        
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			managerPerformmance.setManager_id(user.getId());
			ManagerPerformmanceSum managerPerformmanceSum = managerPerformmanceService.finManagerPerformmanceSumById(user.getId());
			
			
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
					returnMap.put(JRadConstants.SUCCESS, false);
					returnMap.put("mess", "当天已经提交过，不能重复提交");
					return returnMap;
				}
				ManagerPerformmanceSum.setId(managerPerformmanceSum.getId());
				managerPerformmanceService.updateManagerPerformmanceSum(ManagerPerformmanceSum);
				
				
			}
			ManagerPerformmance managerPerformmanceold = managerPerformmanceService.finManagerPerformmanceById(user.getId());
			if(managerPerformmanceold==null){
				managerPerformmanceService.insertmanagerPerformmance(managerPerformmance); 
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}else{
				managerPerformmance.setId(managerPerformmanceold.getId());
				managerPerformmanceService.updatemanagerPerformmance(managerPerformmance); 
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			
			}
		returnMap.put("mess", "提交成功");
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("mess", "提交失败");
			returnMap.addGlobalMessage("保存失败");
		}
		
		return returnMap;
	}
	
	
}
