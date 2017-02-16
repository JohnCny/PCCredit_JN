package com.cardpay.pccredit.dateplan.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.dateplan.dao.SysUserDao;
import com.cardpay.pccredit.dateplan.model.JBUser;
import com.cardpay.pccredit.dateplan.model.datePlanModel;
import com.cardpay.pccredit.dateplan.model.dateTimeModel;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class SysUserService {
	@Autowired
	private SysUserDao UserDao;
	
	public List<ManagerBelongMapForm>selectAllGxUser(){
		return UserDao.selectAllGxUser();
	}
	public List<JBUser>selectDepart(@Param(value = "id")String id){
		return UserDao.selectDepart(id);
	}
	public int insertRw(datePlanModel planmodel){
		return UserDao.insertRw(planmodel);
	}
	public List<datePlanModel>selectAllTime(@Param(value = "id")String id){
		return UserDao.selectAllTime(id);
	}
	public List<datePlanModel>selectByUser(@Param(value = "id")String id){
		return UserDao.selectByUser(id);
	}
	public datePlanModel selectByUser1(@Param(value = "id")String id){
		return UserDao.selectByUser1(id);
	}
	public int updateRw(@Param(value = "id")String id){
		return UserDao.updateRw(id);
	}
	public List<datePlanModel>selectdqsl(@Param(value = "id")String id){
		return UserDao.selectdqsl(id);
	}
	public List<datePlanModel>selectmbsl(@Param(value = "id")String id){
		return UserDao.selectmbsl(id);
	}
	public List<datePlanModel>selectwhsl(@Param(value = "id")String id){
		return UserDao.selectwhsl(id);
	}
	public JBUser selectzgUser(@Param(value = "id")String id){
		return UserDao.selectzgUser(id);
	}
	public List<datePlanModel>selectdhsl(@Param(value = "id")String id){
		return UserDao.selectdhsl(id);
	}
	public List<dateTimeModel>selectByTime(@Param(value = "id")String id){
		return UserDao.selectByTime(id);
	}
	public datePlanModel selectAllTime1(@Param(value = "id")String id){
		return UserDao.selectAllTime1(id);
	}
	public List<JBUser>selectAlluser(){
		return UserDao.selectAlluser();
	}

}
