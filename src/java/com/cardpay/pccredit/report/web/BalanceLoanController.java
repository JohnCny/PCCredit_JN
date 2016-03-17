package com.cardpay.pccredit.report.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.service.CustomerTransferFlowService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/balance/loan/*")
@JRadModule("balance.loan")
public class BalanceLoanController extends BaseController{
	
	@Autowired
	private CustomerTransferFlowService customerTransferFlowService;
	
	/**
	 * 贷款余额统计查询
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryBalanceLoan.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView queryExpireLoan(@ModelAttribute CustomerMoveFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/report/balanceLoan/BalanceLoan", request);
		filter.setRequest(request);
	/*	QueryResult<CustomerMoveForm> result =  customerTransferFlowService.findCustomerMoveList(filter);
		JRadPagedQueryResult<CustomerMoveForm> pagedResult = new JRadPagedQueryResult<CustomerMoveForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);*/
		return mv;
	}
	
	
	
}

