package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.form.BankListForm;
import com.cardpay.pccredit.manager.form.DeptMemberForm;
import com.cardpay.pccredit.manager.form.ManagerPerformmanceForm;
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
	//将当天数量累加到总进度
	void updateManagerPerformmanceSum(ManagerPerformmanceSum managerPerformmance);
	//查询总进度和当天进度
	ManagerPerformmanceForm findSumPerformmanceById(@Param(value="managerId")String managerId);
	//查询指定支行进度
	ManagerPerformmanceForm findDeptSumPerformmanceById(@Param(value="orgId")String orgId);
	//查找所有支行
	List<BankListForm> findALlbank();
	//查询指定所有支行总进度
	ManagerPerformmanceForm findALLDeptSumPerformmanceById();
	//更新总进度
	void updateManagerPerformmanceSumInfor(ManagerPerformmanceSum managerPerformmance);
}
