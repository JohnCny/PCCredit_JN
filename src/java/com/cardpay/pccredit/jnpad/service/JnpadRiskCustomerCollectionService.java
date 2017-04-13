package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadRiskCustomerCollectionDao;
import com.cardpay.pccredit.jnpad.model.DelayInfoForm;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlansAction;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class JnpadRiskCustomerCollectionService {
	@Autowired
	JnpadRiskCustomerCollectionDao riskCustomerCollectionDao;
	
	
	
	
	/**
	 * 通过id得到逾期客户催收计划
	 * @param id
	 * @return
	 */
	public RiskCustomerCollectionPlanForm findRiskCustomerCollectionPlanById(String id) {
		return riskCustomerCollectionDao.findRiskCustomerCollectionPlanById(id);
	}
	
	
	/**
	 * 过滤查询逾期催收客户
	 * @param filter
	 * @return
	 */
	public QueryResult<RiskCustomerCollectionPlanForm> findRiskCustomerCollectionPlansByFilter(RiskCustomerCollectionPlanFilter filter) {
		List<RiskCustomerCollectionPlanForm> riskCustomerCollectionPlanForm = riskCustomerCollectionDao.findRiskCustomerCollectionPlans(filter);
		int size = riskCustomerCollectionDao.findCountByFilter(filter);
		QueryResult<RiskCustomerCollectionPlanForm> qs = new QueryResult<RiskCustomerCollectionPlanForm>(size, riskCustomerCollectionPlanForm);
		return qs;
	}

	
	/**
	 * 得到当前客户经理下属经理名下的逾期客户催收信息数量
	 */

	public int findRiskViewSubCollectionPlansCountByFilter(RiskCustomerCollectionPlanFilter filter){
		
		
		return riskCustomerCollectionDao.findRiskViewSubCollectionPlansCountByFilter(filter);
		
	}


	public List<RiskCustomerCollectionPlansAction> findRiskCustomerCollectionPlansActionByCollectionPlanId(
			String collectionPlanId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询风险客户
	 */
	public List<RiskCustomer> findRiskCustomersByFilter(RiskCustomerFilter filter) {
		//return commonDao.findObjectsByFilter(RiskCustomer.class, filter);
		List<RiskCustomer> riskCustomers = riskCustomerCollectionDao.findRiskCustomersByFilter(filter);
		return riskCustomers;
	}


	public List<Dict> getProductIdAndName(String customerId) {
		// TODO Auto-generated method stub
		 return riskCustomerCollectionDao.getProductIdAndName(customerId);
	}

//获取客户逾期信息
	public DelayInfoForm getDelayInfoByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		return riskCustomerCollectionDao.getDelayInfoByCustomerId(customerId);
	}

	//获取客户经理名下客户逾期信息
	public List<DelayInfoForm> getCustomerRiskInfo(String managerId) {
		// TODO Auto-generated method stub
		return riskCustomerCollectionDao.getCustomerRiskInfo(managerId);
	}
}
