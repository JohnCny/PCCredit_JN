package com.cardpay.pccredit.tdyjcx;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.customeradd.model.CIPERSONBASINFO;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.report.web.AfterLoanCollectController;
import com.cardpay.pccredit.tdyjcx.model.MaintenanceForm;
import com.cardpay.pccredit.tdyjcx.model.ManagerPerformmance;
import com.cardpay.pccredit.tdyjcx.model.Tdyjcx;
import com.cardpay.pccredit.tdyjcx.service.TdyjcxService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
/**
 * 
 * 描述 ：团队业绩查询
 * @author  郑博
 *
 * 2014-11-10 下午3:32:01
 */
@Controller
@RequestMapping("/manager/tdyjcx/*")
@JRadModule("manager.khgl")
public class TdyjcxController extends BaseController{
	private static final Logger logger = Logger.getLogger(TdyjcxController.class);
	
	@Autowired
	private TdyjcxService service;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private MaintenanceDao maintenanceDao;
	@Autowired
	private IntoPiecesService intoPiecesService;
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 团队业绩  因为要显示到叶面的有同时有台帐进件两种类的内容所以用一个容器类Tdyjcx匹配后填装
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "tdyjcx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView khjljyxx(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customeradd/Tdyjcx", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm>forms=new ArrayList<AccountManagerParameterForm>();
		//查询客户经理
		//不是客户经理的显示全部客户经理
		if(user.getUserType()!=1){
			forms=service.findcustomermanager();
		}else{
			//客户经理组长显示他及手下客户经理  客户经理则只显示自己
			forms = maintenanceService.findSubListManagerByManagerId(user);
		}
		String customerManagerId = filter.getCustomerManagerId();
		long start = System.currentTimeMillis();
		//result收集下发数据的信息，managerPerformmanceold收集进件的信息
		QueryResult<MaintenanceForm> result = null;
		//List<Tdyjcx> appcount=new ArrayList<Tdyjcx>(); 
		Tdyjcx tx;
		//如果页面 传过来有信息怎显示客户经理手下信息
		List<Tdyjcx> lists=new ArrayList<Tdyjcx>();
		if(customerManagerId!=null && !customerManagerId.equals("")){
			//appcount=service.findappcount(filter);
			result = service.findTdyjcxList(filter);
			if(!result.getItems().isEmpty() ){
			    tx=new Tdyjcx();
				tx.setUserId(result.getItems().get(0).getUserId());
				tx.setUserName(result.getItems().get(0).getUserName());
				tx.setUser_type(result.getItems().get(0).getUser_type());
				tx.setApplycount(service.findappcount1(filter)); //进件总数
				tx.setCustomercount(service.fiindcustomercount(filter));  //客户数
				tx.setGivemoneycount(result.getItems().get(0).getCustidcount()); //成功放款客户数
				tx.setReqlmtsum(result.getItems().get(0).getReqlmtsum());
				tx.setBalamtsum(result.getItems().get(0).getBalamtsum());
				tx.setDlayamtsum(result.getItems().get(0).getDlayamtsum());
				tx.setBadAmountsum(service.findbadAmountsum(filter));
				if(tx.getCustomercount() == 0){
					tx.setZhlv("0");
				}else{
					tx.setZhlv(new BigDecimal(tx.getGivemoneycount())
					   .divide(new BigDecimal(tx.getCustomercount()),
					   4, BigDecimal.ROUND_HALF_UP)
			           .multiply(new BigDecimal("100")).toString());
				}
				lists.add(tx);
			}
		}else{
			//如果页面穿过来没有信息则显示所有客户经理手下信息
			if(forms.size()>0){
				    //用TotalCount纪录条数
			    	filter.setCustomerManagerIds(forms);
			    	result = service.findTdyjcxList(filter);
			    	List<MaintenanceForm> plans=result.getItems();
			    	if(!plans.isEmpty()){
					    //因为要显示到叶面的有两个类的内容所以用一个容器类Tdyjcx填装
			    		for(int i=0;i<result.getItems().size();i++){
						    tx=new Tdyjcx();
							tx.setUserId(result.getItems().get(i).getUserId());
							tx.setUserName(result.getItems().get(i).getUserName());
							tx.setUser_type(result.getItems().get(i).getUser_type());
							filter.setCustomerManagerId(result.getItems().get(i).getUserId());//添加    查询指定客户经理名下的客户条件
							tx.setApplycount(service.findappcount1(filter)); //进件总数
							tx.setCustomercount(service.fiindcustomercount(filter));  //客户数
							tx.setGivemoneycount(result.getItems().get(i).getCustidcount());//成功放款客户数
							tx.setReqlmtsum(result.getItems().get(i).getReqlmtsum());
							tx.setBalamtsum(result.getItems().get(i).getBalamtsum());
							tx.setDlayamtsum(result.getItems().get(i).getDlayamtsum());
							tx.setBadAmountsum(service.findbadAmountsum(filter));
							//计算转化率 (成功放款客户数/客户数)
							if(tx.getCustomercount() == 0){
								tx.setZhlv("0");
							}else{
								tx.setZhlv(new BigDecimal(tx.getGivemoneycount())
								   .divide(new BigDecimal(tx.getCustomercount()),
								   4, BigDecimal.ROUND_HALF_UP)
						           .multiply(new BigDecimal("100")).toString());
							}
							lists.add(tx);
			    		}
			    	}
			}else{
				//直接返回页面
				return mv;
			}
		}
		long end = System.currentTimeMillis();
		logger.info("贷款汇总查询时间花费：" + (end - start) + "毫秒");
		JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		mv.addObject("lists", lists);
		return mv;
	}
	
	/**
	 * 
	 * 进件情况
	 * */
	@ResponseBody
	@RequestMapping(value = "tdyjjjcx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		QueryResult<IntoPieces> result=null;
		//String userId = user.getId();
		String userId=filter.getUserId();
		//客户经理
		if(user.getUserType() ==1){
			filter.setUserId(userId);
		}
		result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(
				filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/customeradd/intopieces_customer_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);


		return mv;
	}
	
	//显示维护信息--建议
		@ResponseBody
		@RequestMapping(value = "report_jy.page")
		public AbstractModelAndView report_jy(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/customeradd/report_jy", request);
			String appId = RequestHelper.getStringValue(request, "appId");
			String urlType = RequestHelper.getStringValue(request, "urlType");
			if (StringUtils.isNotEmpty(appId)) {
				LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
				String tableContent = getFromBASE64(localExcel.getSheetJy()).replaceAll("\n", "<br>").replace("><br><", "><");
				mv.addObject("tableContent", tableContent);
				mv.addObject("appId", appId);
				mv.addObject("urlType", urlType);
				//查询权限 非本人只能查看 不能操作
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				boolean lock = false;
				if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
					lock = true;
				}
				mv.addObject("lock", lock);
			}
			return mv;
		}
		//base64解码
		public static String getFromBASE64(String s) { 
	    	if (s == null) return null; 
	    	BASE64Decoder decoder = new BASE64Decoder(); 
	    	try { 
	    	byte[] b = decoder.decodeBuffer(s); 
	    	return new String(b); 
	    	} catch (Exception e) { 
	    	return null; 
	    	} 
		}
	
}
