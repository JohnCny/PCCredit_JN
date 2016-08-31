package com.cardpay.pccredit.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.ManagerPerformmanceDao;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceSum;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

@Service
public class ManagerPerformmanceService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	ManagerPerformmanceDao managerPerformmanceDao;
	
	public void insertmanagerPerformmance(ManagerPerformmance managerPerformmance) {
		
		String id = IDGenerator.generateID();
		managerPerformmance.setId(id);
		managerPerformmance.setCrateday(new Date());
		commonDao.insertObject(managerPerformmance);
		
	}

	public List<DeptMemberForm> findMumberByDeptId(String id) {
		// TODO Auto-generated method stub
		return managerPerformmanceDao.findMumberByDeptId(id);
	}

	public ManagerPerformmance finManagerPerformmanceById(String managerId) {
		// TODO Auto-generated method stub
		return managerPerformmanceDao.finManagerPerformmanceById(managerId);
	}

	public ManagerPerformmanceSum finManagerPerformmanceSumById(String managerId) {
		// TODO Auto-generated method stub
		
		return managerPerformmanceDao.finManagerPerformmanceSumById(managerId);
	}

	public void insertManagerPerformmanceSum(ManagerPerformmanceSum managerPerformmance) {
		
		
		commonDao.insertObject(managerPerformmance);
	}

	public void updateManagerPerformmanceSum(ManagerPerformmanceSum managerPerformmance) {
		
		
		managerPerformmanceDao.updateManagerPerformmanceSum(managerPerformmance);
	}

	public void updatemanagerPerformmance(ManagerPerformmance managerPerformmance) {
		
		commonDao.updateObject(managerPerformmance);
	}

	
	
}
