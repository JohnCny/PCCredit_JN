package com.cardpay.pccredit.manager.dao;

import java.util.List;
import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.manager.model.VisitRegistLedger;
import com.wicresoft.util.annotation.Mapper;


/**
 * OtherMusidataInputDao类的描述
 * @created on 2017年6月30日15:20:04
 * @version $Id:$
 */
@Mapper
public interface OtherMusidataInputDao {
	
	//查询在产品期限内的产品
	List<VisitRegistLedger>  findVisitRegistLedgerByFilter(DimensionalFilter filter);
		
	//查询在产品期限内的产品数量
	public int findVisitRegistLedgerCountByFilter(DimensionalFilter filter);
	
}
