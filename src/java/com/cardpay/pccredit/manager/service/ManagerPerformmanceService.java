package com.cardpay.pccredit.manager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

@Service
public class ManagerPerformmanceService {

	@Autowired
	private CommonDao commonDao;
	
	public void insertmanagerPerformmance(ManagerPerformmance managerPerformmance) {
		
		String id = IDGenerator.generateID();
		managerPerformmance.setId(id);
		managerPerformmance.setCrateday(new Date());
		commonDao.insertObject(managerPerformmance);
		
	}

	
	
}
