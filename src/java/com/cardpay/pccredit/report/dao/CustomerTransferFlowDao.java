package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.CustomerMoveFilter;
import com.cardpay.pccredit.report.model.CustomerMoveForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerTransferFlowDao {
    
	List<CustomerMoveForm> findCustomerTransferList(CustomerMoveFilter filter);
	int findCustomerMoveCountList(CustomerMoveFilter filter);
	public List<CustomerMoveForm> getCustomerMovelList(CustomerMoveFilter filter);
}