package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.model.ManagerPerformmance;
import com.cardpay.pccredit.manager.model.ManagerPerformmanceSum;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface ManagerPerformmanceDao {

	//查询部门成员
	List<DeptMemberForm> findMumberByDeptId(@Param(value="id") String id);
	//查询客户经理当天进度
	ManagerPerformmance finManagerPerformmanceById(@Param(value="managerId") String managerId);
	//查询客户经理总进度
	ManagerPerformmanceSum finManagerPerformmanceSumById(@Param(value="managerId") String managerId);
	
	void updateManagerPerformmanceSum(ManagerPerformmanceSum managerPerformmance);
	
}
