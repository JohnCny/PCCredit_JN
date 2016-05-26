package com.cardpay.pccredit.jnpad.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

@Service
public class JnpadCustomerInfoInsertServ‎ice {
	@Autowired
	private CommonDao commonDao;
//插入客户信息
	public String customerInforInsert(CustomerInfo customerinfor) {
		
			String id = IDGenerator.generateID();
			customerinfor.setId(id);
			customerinfor.setCreatedTime(new Date());
			commonDao.insertObject(customerinfor);
			return customerinfor.getId();
		}
	}


