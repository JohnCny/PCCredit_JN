package com.cardpay.pccredit.manager.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.service.ManagerPerformmanceService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
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
		JRadModelAndView mv = new JRadModelAndView("/manager/performmance/performmance_sum", request);
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
			managerPerformmanceService.insertmanagerPerformmance(managerPerformmance); 
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			
			
			
			
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.addGlobalMessage("保存失败");
		}
		
		return returnMap;
	}
	
	
}
