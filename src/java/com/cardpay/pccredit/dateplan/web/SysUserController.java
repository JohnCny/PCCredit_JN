package com.cardpay.pccredit.dateplan.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerMarketingFilter;
import com.cardpay.pccredit.customer.model.MarketingPlanWeb;
import com.cardpay.pccredit.dateplan.dao.SysUserDao;
import com.cardpay.pccredit.dateplan.model.DisplayUser;
import com.cardpay.pccredit.dateplan.model.JBUser;
import com.cardpay.pccredit.dateplan.model.datePlanModel;
import com.cardpay.pccredit.dateplan.model.dateTimeModel;
import com.cardpay.pccredit.dateplan.service.SysUserService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerManagerVo;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/sys/user/*")
@JRadModule("sys.user")
public class SysUserController {
	@Autowired
	private SysUserService UserService;
	/**
	 * 查询主管下面客户经理/客户经理的今日计划
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView view(@ModelAttribute ManagerBelongMapForm filter, HttpServletRequest request) {
		Integer b=0;
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
		List<ManagerBelongMapForm> result=UserService.selectAllGxUser();
		List list=new ArrayList<JBUser>();
		if(user.getUserType()!=1){
			for(int a=0;a<result.size();a++){
				JBUser name=UserService.selectzgUser(result.get(a).getParentId());
				list.add(a, name);
			}
			JRadModelAndView mv = new JRadModelAndView(
					"/dateplan/sys_user", request);
			mv.addObject("result", list);
			return mv;
		}else{
			for(int a=0;a<result.size();a++){
				if(customerManagerId.equals(result.get(a).getParentId())){
					b=1;
				}
			}
			if(b==1){
				List<JBUser>result1=UserService.selectDepart(customerManagerId);
				for(int c=0;c<result1.size();c++){
					if(customerManagerId.equals(result1.get(c).getId())){
						result1.remove(c);
					}
				}
				JRadModelAndView mv = new JRadModelAndView(
						"/dateplan/sys_user", request);
				mv.addObject("result", result1);
				return mv;
			}
		}
		return null;
	}
	/**
	 * 跳转到任务添加页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "creat.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView view1( HttpServletRequest request) {
		String id=request.getParameter("id");
		String temp[]=id.split("@");
			JRadModelAndView mv = new JRadModelAndView(
					"/dateplan/createplan", request);
			DisplayUser user=new DisplayUser();
			user.setId(temp[0]);
			user.setName(temp[1]);
			mv.addObject("user", user);
			return mv;
	}
	/**
	 * 添加任务
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "creat1.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap view2(@ModelAttribute datePlanModel filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		filter.setId(IDGenerator.generateID());
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
		filter.setGxuserid(customerManagerId);
		filter.setUserid(request.getParameter("id"));
		filter.setZdtime(new Date());
		filter.setMbsl(Integer.parseInt(request.getParameter("mbsl")));
		filter.setZt(0);
		filter.setWcqk(0);
		filter.setDcsl(Integer.parseInt(request.getParameter("dcsl")));
		filter.setDhsl(Integer.parseInt(request.getParameter("dhsl")));
		filter.setWhsl(Integer.parseInt(request.getParameter("whsl")));
		DisplayUser user1=new DisplayUser();
		int a=UserService.insertRw(filter);
		if(a>0){
			returnMap.put("message","操作成功");
		}else{
			returnMap.put("message","操作失败");
		}
		return returnMap;
	}
	
	/**
	 * 查询选中客户经理是否已经添加过当前任务
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "creat2.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public JRadReturnMap view3(@ModelAttribute datePlanModel filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		Integer c=0;
		ArrayList list=new ArrayList();
		String id=request.getParameter("id");
		String temp[]=id.split("@");
		List<datePlanModel> result=UserService.selectAllTime(temp[0]);
		for(int a=0;a<result.size();a++){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String newDate = sdf.format(result.get(a).getZdtime());
			list.add(newDate.substring(0,10).trim());
		}
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String newDate1 = sdf.format(date);
		
		for(int b=0;b<list.size();b++){
			if(newDate1.substring(0, 10).trim().equals(list.get(b))){
				c=1;
			}
		}
		if(c>0){
			returnMap.put("message","1");
		}else{
			returnMap.put("message","0");
		}
		return returnMap;
	}
	
	/**
	 * 我的任务
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectplan.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView selectplan(@ModelAttribute ManagerBelongMapForm filter, HttpServletRequest request) {
		datePlanModel datePlanModel=new datePlanModel();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
			Date date=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String newDate = sdf.format(date);
			String date1=newDate.substring(0,10).trim();
			List<datePlanModel>result=UserService.selectByUser(customerManagerId);
			for(int a=0;a<result.size();a++){
				String time = sdf.format(result.get(a).getZdtime());
				if(time.substring(0, 10).trim().equals(date1)){
					datePlanModel.setDcsl(result.get(a).getDcsl());
					datePlanModel.setDhsl(result.get(a).getDhsl());
					datePlanModel.setMbsl(result.get(a).getMbsl());
					datePlanModel.setWhsl(result.get(a).getWhsl());
					datePlanModel name=UserService.selectByUser1(result.get(a).getGxuserid());
					datePlanModel.setName(name.getName());
					if(!result.get(a).getZt().equals(1)){
						int count=UserService.updateRw(result.get(a).getId());
					}
				}
			}
			if(datePlanModel.getName()==null){
				datePlanModel.setName("暂无制定人");
			}
			if(datePlanModel.getDcsl()==null){
				datePlanModel.setDcsl(0);
			}
			if(datePlanModel.getDhsl()==null){
				datePlanModel.setDhsl(0);
			}
			if(datePlanModel.getWhsl()==null){
				datePlanModel.setWhsl(0);
			}
			if(datePlanModel.getMbsl()==null){
				datePlanModel.setMbsl(0);
			}
			JRadModelAndView mv = new JRadModelAndView(
					"/dateplan/userplan", request);
			mv.addObject("datePlanModel", datePlanModel);
			return mv;
	}
	/**
	 * 跳转到任务完成详情页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "rwxq.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView view4(@ModelAttribute datePlanModel filter, HttpServletRequest request) {
		Integer dqsll=0;
		Integer mbsll=0;
		Integer whsll=0;
		Integer dhsll=0;
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
		String mbsl=request.getParameter("mbsl");
		String dcsl=request.getParameter("dcsl");
		String dhsl=request.getParameter("dhsl");
		String whsl=request.getParameter("whsl");
		filter.setDcsl(Integer.parseInt(dcsl));
		filter.setDhsl(Integer.parseInt(dhsl));
		filter.setMbsl(Integer.parseInt(mbsl));
		filter.setWhsl(Integer.parseInt(whsl));
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time=sdf.format(date).substring(0, 10).trim();
		try {
			List<datePlanModel> result=UserService.selectdqsl(customerManagerId);
			if(result!=null){
			for(int a=0;a<result.size();a++){
				String newDate = sdf.format(result.get(a).getZdtime());
				if(newDate.substring(0, 10).trim().equals(time)){
					dqsll+=1;
				}
			}}
			List<datePlanModel> result1=UserService.selectmbsl(customerManagerId);
			if(result1!=null){
			for(int a=0;a<result1.size();a++){
				String newDate = sdf.format(result1.get(a).getZdtime());
				if(newDate.substring(0, 10).trim().equals(time)){
					mbsll+=1;
				}}
			}
			List<datePlanModel> result2=UserService.selectwhsl(customerManagerId);
			if(result2!=null){
			for(int a=0;a<result2.size();a++){
				String newDate = sdf.format(result2.get(a).getZdtime());
				if(newDate.substring(0, 10).trim().equals(time)){
					whsll+=1;
				}
			}}
			List<datePlanModel> result3=UserService.selectdhsl(customerManagerId);
			if(result3!=null){
				for(int a=0;a<result3.size();a++){
					String newDate = sdf.format(result3.get(a).getZdtime());
					if(newDate.substring(0, 10).trim().equals(time)){
						dhsll+=1;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		filter.setMbsll(mbsll);
		filter.setDhsll(dhsll);
		filter.setDqsll(dqsll);
		filter.setWhsll(whsll);
			JRadModelAndView mv = new JRadModelAndView(
					"/dateplan/userxqplan", request);
			mv.addObject("result", filter);
			return mv;
	}
	/**
	 * 指定客户经理的任务详情
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "rwxq1.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView view5(@ModelAttribute datePlanModel filter, HttpServletRequest request) {
	/*	dateTimeModel dtm1=new dateTimeModel();
		ArrayList <dateTimeModel>dtlist=new ArrayList<dateTimeModel>();*/
		Integer dqsll=0;
		Integer mbsll=0;
		Integer whsll=0;
		Integer dhsll=0;
		datePlanModel datePlanModel=new datePlanModel();
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String newDate1 = sdf.format(date).substring(0, 10).trim();
		String id=request.getParameter("id");
		String temp[]=id.split("@");
	/*	List<dateTimeModel> time=UserService.selectByTime(id);
		for(int dt=0;dt<time.size();dt++){
			dtm1.setTime(sdf.format(time.get(dt).getTime()).substring(0, 10).trim().toString());
			dtlist.set(dt, dtm1);
		}*/
		List<datePlanModel> resul=UserService.selectByUser(temp[0]);
		for(int a=0;a<resul.size();a++){
			String newDate = sdf.format(resul.get(a).getZdtime());
			if(newDate.substring(0,10).trim().equals(newDate1)){
				datePlanModel.setDcsl(resul.get(a).getDcsl());
				datePlanModel.setDhsl(resul.get(a).getDhsl());
				datePlanModel.setWhsl(resul.get(a).getWhsl());
				datePlanModel.setMbsl(resul.get(a).getMbsl());
				datePlanModel.setName(temp[1]);
				datePlanModel.setZt(resul.get(a).getZt());
			}
		}
		if(datePlanModel.getDcsl()==null){
			datePlanModel.setDcsl(0);
		}
		if(datePlanModel.getDhsl()==null){
			datePlanModel.setDhsl(0);
		}
		if(datePlanModel.getWhsl()==null){
			datePlanModel.setWhsl(0);
		}
		if(datePlanModel.getMbsl()==null){
			datePlanModel.setMbsl(0);
		}
		try {
			List<datePlanModel> result=UserService.selectdqsl(temp[0]);
			if(result!=null){
			for(int a=0;a<result.size();a++){
				String newDate = sdf.format(result.get(a).getZdtime());
				if(newDate.substring(0, 10).trim().equals(newDate1)){
					dqsll+=1;
				}
			}}
			List<datePlanModel> result1=UserService.selectmbsl(temp[0]);
			if(result1!=null){
			for(int a=0;a<result1.size();a++){
				String newDate = sdf.format(result1.get(a).getZdtime());
				if(newDate.substring(0, 10).trim().equals(newDate1)){
					mbsll+=1;
				}
			}}
			List<datePlanModel> result2=UserService.selectwhsl(temp[0]);
			if(result2!=null){
			for(int a=0;a<result2.size();a++){
				String newDate = sdf.format(result2.get(a).getZdtime());
				if(newDate.substring(0, 10).trim().equals(newDate1)){
					whsll+=1;
				}
			}}
			List<datePlanModel> result3=UserService.selectdhsl(temp[0]);
			if(result3!=null){
				for(int a=0;a<result3.size();a++){
					String newDate = sdf.format(result3.get(a).getZdtime());
					if(newDate.substring(0, 10).trim().equals(newDate1)){
						dhsll+=1;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		datePlanModel.setMbsll(mbsll);
		datePlanModel.setDhsll(dhsll);
		datePlanModel.setDqsll(dqsll);
		datePlanModel.setWhsll(whsll);
		JRadModelAndView mv = new JRadModelAndView(
				"/dateplan/userxqplan2", request);
		mv.addObject("result", datePlanModel);
		//mv.addObject("result1", dtlist);
		return mv;
	}
}
