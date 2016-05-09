package com.cardpay.pccredit.jnpad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cardpay.pccredit.jnpad.dao.JnIpadCustAppInfoXxDao;
import com.cardpay.pccredit.jnpad.dao.JnIpadUserLoginDao;

@Service
public class JnIpadCustAppInfoXxService {
	
	@Autowired
	private JnIpadCustAppInfoXxDao jnIpadCustAppInfoDao;
	
	public int findCustomerApplicationInfoCount(String userId,
												String status1,
												String status2,
												String status3,
												String status4){
		return jnIpadCustAppInfoDao.findCustomerApplicationInfoCount(userId,
																     status1,
																     status2,
																     status3,
																     status4);
	}
}
