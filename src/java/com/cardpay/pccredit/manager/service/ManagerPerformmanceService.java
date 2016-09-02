package com.cardpay.pccredit.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.ManagerPerformmanceDao;
import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
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

	//查询当天进度和总进度
	public ManagerPerformmanceForm findSumPerformmanceById(String Id){
		
		return managerPerformmanceDao.findSumPerformmanceById(Id);
	
}
	//查询指定机构当天进度和总进度
	public ManagerPerformmanceForm findDeptSumPerformmanceById(String id) {
		// TODO Auto-generated method stub
		return managerPerformmanceDao.findDeptSumPerformmanceById(id);
	}
	//查找所有支行
	public List<BankListForm> findALlbank() {
		// TODO Auto-generated method stub
		return managerPerformmanceDao.findALlbank();
	}

	public ManagerPerformmanceForm findALLDeptSumPerformmanceById() {
		// TODO Auto-generated method stub
		return managerPerformmanceDao.findALLDeptSumPerformmanceById();
	}
}
