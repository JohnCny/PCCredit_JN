package com.cardpay.pccredit.manager.dao;

import java.util.List;

import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.manager.model.LoanApproved;
import com.cardpay.pccredit.manager.model.LoanRefused;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ManagerOtherInfoInputDao {
	
	
	/**
	 * 放款台账
	 */
	public List<LoanApproved> findLoanApprovedByFilter(DimensionalFilter filter);
	public int findLoanApprovedCountByFilter(DimensionalFilter filter);
	
	
	/**
	 * @param 拒绝台账
	 */
	public List<LoanRefused> findLoanRefusedByFilter(DimensionalFilter filter);
	public int findLoanRefusedCountByFilter(DimensionalFilter filter);
}
