package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.CustomerTransferFlowDao;
import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.model.CustomerMove;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class CustomerTransferFlowService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerTransferFlowDao customerTransferFlowDao;
	/**
	 * 客户经理移交流水查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerMoveForm> findCustomerMoveList(CustomerMoveFilter filter){
		List<CustomerMoveForm> list = customerTransferFlowDao.findCustomerTransferList(filter);
		int size = customerTransferFlowDao.findCustomerMoveCountList(filter);
		QueryResult<CustomerMoveForm> result = new QueryResult<CustomerMoveForm>(size,list);
		return result;
	} 
	
	public List<CustomerMoveForm> getCustomerMovelList(CustomerMoveFilter filter){
		return customerTransferFlowDao.getCustomerMovelList(filter);
	}
}
