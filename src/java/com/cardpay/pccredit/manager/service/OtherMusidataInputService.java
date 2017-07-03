package com.cardpay.pccredit.manager.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.manager.dao.AccountManagerParameterDao;
import com.cardpay.pccredit.manager.dao.OtherMusidataInputDao;
import com.cardpay.pccredit.manager.dao.comdao.AccountManagerParameterComdao;
import com.cardpay.pccredit.manager.model.VisitRegistLedger;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;


/**
 * OtherMusidataInputService类的描述
 * @created on 2017年6月30日14:07:33
 * @version $Id:$
 */
@Service
public class OtherMusidataInputService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private OtherMusidataInputDao otherMusidataInputDao;
	
	
	
	public QueryResult<VisitRegistLedger> findVisitRegistLedgerByFilter(DimensionalFilter filter) {
		List<VisitRegistLedger> list = otherMusidataInputDao.findVisitRegistLedgerByFilter(filter);
		int size = otherMusidataInputDao.findVisitRegistLedgerCountByFilter(filter);
		QueryResult<VisitRegistLedger> qs = new QueryResult<VisitRegistLedger>(size,list);
		return qs;
	
	}
    
	public String insertVisitRegistLedgerParameter(VisitRegistLedger vreg){
	    commonDao.insertObject(vreg);
	    return vreg.getId();
	}
	
	
	public int updateVisitRegistLedgerParameter(VisitRegistLedger vreg) {
		return commonDao.updateObject(vreg);
	}
	
	
	public VisitRegistLedger findVisitRegistLedgerParameterById(String id) {
		return commonDao.findObjectById(VisitRegistLedger.class, id);
	}
	
}