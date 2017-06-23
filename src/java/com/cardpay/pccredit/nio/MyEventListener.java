package com.cardpay.pccredit.nio;

import org.springframework.beans.factory.annotation.Autowired;

import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.BusinessModel;

public class MyEventListener {
	
	@Autowired    
	private CommonDao commonDao;
	
	public void saveObject(BusinessModel model){
		commonDao.insertObject(model);
	}
}
