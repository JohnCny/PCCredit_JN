package com.cardpay.pccredit.jnpad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadCustomerSelectDao;
import com.cardpay.pccredit.jnpad.model.CIPERSONBASINFO;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;

/**
 * 客户信息查询
 * @author sealy
 *
 */
@Service

public class JnpadCustomerSelectService {
	@Autowired
	private JnpadCustomerSelectDao jnpadCustomerSelectDao;
	
	
	/**
	 * 根据证件号码查询
	 * @return
	 */
	public CustomerInfo selectCustomerInfoByCardId(String cardId){
		
		return jnpadCustomerSelectDao.selectCustomerInforByCardId(cardId);
		
	}
	
	
	/**
	 * 
	 * 按id查找相应的客户基本信息
	 * @return
	 */
	public CIPERSONBASINFO selectCustomerInfoById(String id) {
		// TODO Auto-generated method stub
		return jnpadCustomerSelectDao.selectCustomerInfoById(id);
	}
	
	
	/**
	 * 
	 * 按客户内码id查找相应的客户基本信息
	 * @return
	 */
	public CIPERSONBASINFO selectCustomerByNm(String custid) {
		// TODO Auto-generated method stub
		return jnpadCustomerSelectDao.selectCustomerByNm(custid);
	}





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
