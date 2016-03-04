package com.cardpay.pccredit.manager.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.common.Arith;
import com.cardpay.pccredit.manager.dao.ManagerSalaryDao;
import com.cardpay.pccredit.manager.filter.ManagerCashConfigurationFilter;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.manager.model.ManagerCashConfiguration;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.model.ManagerSalaryParameter;
import com.cardpay.pccredit.manager.model.SalaryParameter;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.cardpay.pccredit.manager.model.TyRiskMargin;
import com.cardpay.pccredit.manager.model.TyRiskMarginSpecific;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:56:18
 */
@Service
public class ManagerSalaryService {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerSalaryDao managerSalaryDao;
	
	@Autowired
	private ManagerCashConfigurationService managerCashConfigurationService;
	
	@Autowired
	private ManagerAssessmentScoreService managerAssessmentScoreService;
	
	@Autowired
	private ManagerPerformanceParametersService managerPerformanceParametersService;
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerSalary> findManagerSalaryByFilter(ManagerSalaryFilter filter) {
		List<ManagerSalary> list = managerSalaryDao.findManagerSalarysByFilter(filter);
		int size = managerSalaryDao.findManagerSalarysCountByFilter(filter);
		QueryResult<ManagerSalary> qs = new QueryResult<ManagerSalary>(size, list);
		return qs;
	}
	
	/**
	 * 插入客户经理薪资
	 * @param riskCustomer
	 * @return
	 */
	public String insertManagerSalary(ManagerSalary managerSalary) {
		if(managerSalary.getCreatedTime() == null){
			managerSalary.setCreatedTime(Calendar.getInstance().getTime());
		}
		if(managerSalary.getModifiedTime() == null){
			managerSalary.setModifiedTime(Calendar.getInstance().getTime());
		}
		commonDao.insertObject(managerSalary);
		return managerSalary.getId();
	}
	/**
	 * 计算客户经理月度薪资
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean calculateMonthlySalary(int year, int month){
		boolean flag = true;
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, 1);
			
			calendar.add(Calendar.MONTH, -1);
			
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			managerSalaryDao.deleteManagerSalaryByYearAndMonth(year, month);
			// 获取客户经理最大层级
			int maxLevel = managerSalaryDao.getMaxManagerLevel();
			for(int i = maxLevel; i > 0; i--){
				calculateLevelSalary(year, month, i);
			}
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 计算某个层级的客户经理的薪资
	 * @param year
	 * @param month
	 * @param level
	 * @return
	 */
	public void calculateLevelSalary(int year, int month, int level){
		// 
		List<SalaryParameter> list = managerSalaryDao.findSalaryParametersByFilter(level, year, month);
		Map<String, ManagerSalary> salaryMap = getManagerSalary(list, year, month);
		
		ManagerSalary managerSalary = null;
		SalaryParameter salaryParameter = null;
		for(int i = 0; i < list.size(); i++){
			salaryParameter = list.get(i);
			/*if("0000000049ea417e0149f05573f81d2e".equals(salaryParameter.getManagerId())){
				int a = 0;
			}*/
			managerSalary = salaryMap.get(salaryParameter.getManagerId());
			// 责任工资
			managerSalary.setDutySalary(salaryParameter.calculateDutySalary());
			// 津贴
			managerSalary.setAllowance(salaryParameter.getAllowance());
			// 底薪
			managerSalary.setBasePay(salaryParameter.getBasePay());
			// 返还金额(等于三年前本月存入)
			managerSalary.setReturnPrepareAmount(salaryParameter.getInsertPrepareAmount());
			
			// 管理绩效
			String managePerformance = "0";
			// 判断是否为叶子节点
			if(!salaryParameter.isLeaf()){
				// 获取客户经理管理绩效利息
				managePerformance = managerSalaryDao.getManagePerformance(managerSalary.getCustomerId(), year, month);
			}
			// 计算并设置绩效工资
			managerSalary.setRewardIncentiveInformation(salaryParameter.calculatePerformanceSalary(managePerformance));
			
			String reward = managerSalary.getRewardIncentiveInformation();
			// 计算并设置存入的风险准备金(乘以风险准备金权数)
			String insertPrepareAmount = Arith.mulReturnStr(getExtractRate(reward),reward);
			insertPrepareAmount = Arith.mulReturnStr(insertPrepareAmount,salaryParameter.getMarginExtractInfo());
			managerSalary.setInsertPrepareAmount(insertPrepareAmount);
			
			// 本月风险准备金余额  = 上月风险准备金余额  + 本月存入的风险准备金 - 返还金额(等于三年前本月存入)
			String reserveBalances = Arith.subReturnStr(Arith.addReturnStr(salaryParameter.getRiskReserveBalances(), managerSalary.getInsertPrepareAmount()), managerSalary.getReturnPrepareAmount());
			managerSalary.setRiskReserveBalances(reserveBalances);
			// 保存客户经理薪资
			insertManagerSalary(managerSalary);
		}
	}
	
	public Map<String, ManagerSalary> getManagerSalary(List<SalaryParameter> list, int year, int month){
		Map<String, ManagerSalary> hm = new HashMap<String, ManagerSalary>();
		ManagerSalary managerSalary = null;
		for(SalaryParameter salaryParameter : list){
			managerSalary = new ManagerSalary();
			managerSalary.setCustomerId(salaryParameter.getManagerId());
			managerSalary.setYear(year+"");
			managerSalary.setMonth(month+"");
			hm.put(managerSalary.getCustomerId(), managerSalary);
		}
		return hm;
	}
	
	/*
	 * 获取提取比率
	 */
	public String getExtractRate(String amount){
		ManagerCashConfigurationFilter filter = new ManagerCashConfigurationFilter();
		QueryResult<ManagerCashConfiguration> qs = managerCashConfigurationService.findManagerCashConfigurationByFilter(filter);
		for(ManagerCashConfiguration cashConfiguration : qs.getItems()){
			if(Arith.compare(amount, cashConfiguration.getStartAmount()) >= 0 
					&& Arith.compare(cashConfiguration.getEndAmount(), amount) >= 0){
				return Arith.divReturnStr(cashConfiguration.getMarginExtractInfo(), "100");
			}
		}
		return "0";
	}
	/**
	 * 得到风险保证余额
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getReturnPrepareAmountById(int year,int month,String id){
		if(StringUtils.isNotEmpty(id)){
			return managerSalaryDao.getReturnPrepareAmountById(year, month, id);
		}else{
			return "-1";
		}
	}
	/**
	 * 得到客户经理绩效工资
	 * @param year
	 * @param month
	 * @param id
	 * @return
	 */
	public String getRewardIncentiveInformation(int year,int month,String id){
		if(StringUtils.isNotEmpty(id)){
			return managerSalaryDao.getRewardIncentiveInformation(year, month, id);
		}else{
			return "-1";
		}
	}
	
	/**
	 * 计算客户经理月度薪资(太原)
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean calculateMonthlySalaryTy(int year, int month){
		boolean flag = true;
		try{
			managerSalaryDao.deleteManagerSalaryByYearAndMonth(year, month);
			//获取所有客户经理
			List<AccountManagerParameterForm> accountList = accountManagerParameterService.findAccountManagerParameterAll();
			//循环计算客户经理当月工资
			for(int i=0;i<accountList.size();i++){
				//获取此客户经理的绩效参数
				TyPerformanceParameters parameter = managerPerformanceParametersService.getParameterByLevel(accountList.get(i).getUserId());
				if(parameter!=null){
					calculateSalaryExactly(year, month,parameter,accountList.get(i).getUserId());
				}
			}
			//计算主管额外绩效
			String accountSql = "select b.* from manager_belong_map a,account_manager_parameter b where  a.child_id=b.id and a.parent_id in (select child_id from manager_belong_map where parent_id is null)";
			List<AccountManagerParameter> accountGroupList = commonDao.queryBySql(AccountManagerParameter.class, accountSql, null);
			//循环计算客户经理主管当月工资
			for(int i=0;i<accountGroupList.size();i++){
				calculateSalaryExactlyGroup(year, month,accountGroupList.get(i).getUserId());
			}
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 *具体每月工资计算 （客户经理）
	 */
	private void calculateSalaryExactly(int year,int month,TyPerformanceParameters parameter ,String customerId){
		String date = "";
		if(month<10){
			date = year +"0"+month;
		}else{
			date = year +""+month;
		}
		
		//获得每月绩效参数值
		String sql = "select * from manager_salary_parameter where user_id='"+customerId+"' and month='"+date+"'";
		List<ManagerSalaryParameter> salaryList = commonDao.queryBySql(ManagerSalaryParameter.class, sql, null);
		if(salaryList.size()>0){
			ManagerSalaryParameter salaryParameter = salaryList.get(0);
			//主调绩效
			Double zdPerform = Double.parseDouble(parameter.getA()) * Double.parseDouble(salaryParameter.getZdCount());
			//辅调绩效
			Double fdPerform = Double.parseDouble(parameter.getF()) * Double.parseDouble(salaryParameter.getFdCount());
			//管户绩效
			Double ghPerform = Double.parseDouble(parameter.getB()) * Double.parseDouble(salaryParameter.getTubes());
			//审批绩效
			Double spPerform = Double.parseDouble(parameter.getD()) * Double.parseDouble(salaryParameter.getSpCount());
			//岗位绩效
			Double gwPerform = Double.parseDouble(parameter.getE());
			//完成度
			Double pet = Double.parseDouble(salaryParameter.getCompeterCount())/Double.parseDouble(parameter.getObjectCount());
			//完成绩效
			Double compet = 0.00;
			if(pet>=0.8&&pet<1){
				compet = 600.00;
			}else if(pet>=1&&pet<1.2){
				compet = 1000.00;
			}else if(pet>=1.2){
				compet = 1300.00;
			}
			String competerPet = String.format("%.2f", pet);
			Double monthPerform = zdPerform+fdPerform+ghPerform+spPerform+gwPerform+compet;
			//保存当月工资单
			ManagerSalary salary = new ManagerSalary();
			salary.setYear(year+"");
			salary.setMonth(month+"");
			salary.setCustomerId(customerId);
			salary.setBasePay(parameter.getBasicPerformance());
			salary.setCompeterPet(competerPet);
			salary.setRewardIncentiveInformation(String.valueOf(monthPerform));
			salary.setZdPerform(zdPerform+"");
			salary.setFdPerform(fdPerform+"");
			salary.setSpPerform(spPerform+"");
			salary.setGhPerform(ghPerform+"");
			salary.setGwPerform(gwPerform+"");
			salary.setCompeterPerform(compet+"");
			commonDao.insertObject(salary);
			
		}

	}
	/**
	 *具体每月工资计算 （主管）
	 */
	private void calculateSalaryExactlyGroup(int year,int month ,String customerId){
		//获取组员客户经理id
		String childSql = "select * from manager_salary where customer_id in (select user_id from ACCOUNT_MANAGER_PARAMETER where id in( SELECT a.CHILD_ID FROM MANAGER_BELONG_MAP a ,ACCOUNT_MANAGER_PARAMETER b where a.PARENT_ID=b.id and b.USER_ID='"+customerId+"')) and year='"+year+"' and month='"+month+"'";
		List<ManagerSalary> childsSalaryList = commonDao.queryBySql(ManagerSalary.class,childSql , null);
		//团队完成数
		Double groupAll=0.00;
		//团队完成比例
		Double groupAllPet=0.00;
		//主管额外绩效
		Double groupSalary =0.00;
		for(int i=0;i<childsSalaryList.size();i++){
			groupAll+=Double.parseDouble(childsSalaryList.get(i).getCompeterPet());
		}
		groupAllPet = groupAll/childsSalaryList.size();
		if(groupAllPet>=0.8){
			groupSalary = 1000.00;
		}else{
			groupSalary = 500.00;
		}
		List<ManagerSalary> groupSalaryList = commonDao.queryBySql(ManagerSalary.class,"select * from manager_salary where customer_id='"+customerId+"' and  year='"+year+"' and month='"+month+"'" , null);
		if(groupSalaryList.size()>0){
			ManagerSalary group = groupSalaryList.get(0);
			group.setGroupSalary(groupSalary+"");
			group.setRewardIncentiveInformation(Double.parseDouble(group.getRewardIncentiveInformation())+groupSalary+"");
			commonDao.updateObject(group);
		}
	}

	
	/*
	 * 逾期扣款比例
	 */
	private double getOverLoanPer(double overdueLoan){
		if(overdueLoan==0){
			return 0;
		}else if(overdueLoan<=0.005){
			return 0.05;
		}else if(overdueLoan>0.005&&overdueLoan<=0.01){
			return 0.1;
		}else if(overdueLoan>0.01&&overdueLoan<=0.015){
			return  0.2;
		}else{
			return 0.3;
		}
	}
	/*
	 * 新增风险保证金计算
	 */
	private double getAddRisk(double monthPerform){
		double addRisk = 0;
		if(monthPerform<=2000){
			return addRisk;
		}else if(monthPerform>2000&&monthPerform<=5000){
			addRisk = (monthPerform-2000)*0.1;
		}else{
			addRisk=3000*0.1+(monthPerform-5000)*0.2;
		}
		return addRisk;
	}
	
	/*
	 * 返还风险保证金
	 */
	private double outRiskMargin(int year,int month,String customerId){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		
		calendar.add(Calendar.MONTH, -18);
		//获取18个月前的时间
		int  year_18 = calendar.get(Calendar.YEAR);
		int month_18 = calendar.get(Calendar.MONTH);
		//查询18月前风险保证金
		TyRiskMarginSpecific specific = getRiskMarginSpecific(year_18,month_18,customerId);
		if(specific==null){
			return 0;
		}else{
			return (Double.parseDouble(specific.getInRiskMargin())-Double.parseDouble(specific.getDeductRiskMargin()))>0?Double.parseDouble(specific.getInRiskMargin())-Double.parseDouble(specific.getDeductRiskMargin()):0;
		}
	}
	
	/*
	 * 根据年月查询风险保证金log
	 */
	public TyRiskMarginSpecific getRiskMarginSpecific(int year,int month,String customerId){
		String sql ="select * from ty_risk_margin_specific where risk_id in (select id from ty_risk_margin where customer_id='"+customerId+"') and year='"+year+"' and month='"+month+"'";
		List<TyRiskMarginSpecific> tyRiskMarginSpecifics = commonDao.queryBySql(TyRiskMarginSpecific.class, sql, null);
		if(tyRiskMarginSpecifics.size()>0){
			return tyRiskMarginSpecifics.get(0);
		}else{
			return null;
		}
	}
	/*
	 * 根据customerId获取总风险保证金
	 */
	public TyRiskMargin getRiskMarginByCustomerId(String customerId){
		String sql = "select * from ty_risk_margin where customer_id='"+customerId+"'";
		List<TyRiskMargin> tyRiskMarginsList = commonDao.queryBySql(TyRiskMargin.class, sql, null);
		if(tyRiskMarginsList.size()>0){
			return tyRiskMarginsList.get(0);
		}else{
			return null;
		}
	}
	/*
	 * 绩效导出
	 */
	public void getExportData(ManagerSalaryFilter filter,HttpServletResponse response) throws Exception{
		List<ManagerSalary> salaryList = managerSalaryDao.findManagerSalarys(filter);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("客户经理绩效详情");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("年份");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("月份");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("小微团队");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("客户经理");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);  
        cell.setCellValue("底薪");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);  
        cell.setCellValue("主管绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);  
        cell.setCellValue("主调绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);  
        cell.setCellValue("辅调绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);  
        cell.setCellValue("管户绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);  
        cell.setCellValue("审批绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);  
        cell.setCellValue("岗位绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);  
        cell.setCellValue("完成度绩效");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);  
        cell.setCellValue("总绩效");  
        cell.setCellStyle(style);
        for(int i=0;i<salaryList.size();i++){
        	row = sheet.createRow((int) i + 1);
        	ManagerSalary salary = salaryList.get(i);
        	row.createCell((short) 0).setCellValue((String) salary.getYear());  
        	row.createCell((short) 1).setCellValue((String) salary.getMonth());  
        	row.createCell((short) 2).setCellValue((String) salary.getShortName());  
        	row.createCell((short) 3).setCellValue((String) salary.getManagerName());  
        	row.createCell((short) 4).setCellValue((String) salary.getBasePay());  
        	row.createCell((short) 5).setCellValue((String) salary.getGroupSalary());  
        	row.createCell((short) 6).setCellValue((String) salary.getZdPerform());  
        	row.createCell((short) 7).setCellValue((String) salary.getFdPerform());  
        	row.createCell((short) 8).setCellValue((String) salary.getGhPerform());  
        	row.createCell((short) 9).setCellValue((String) salary.getSpPerform());  
        	row.createCell((short) 10).setCellValue((String) salary.getGwPerform());  
        	row.createCell((short) 11).setCellValue((String) salary.getCompeterPerform());  
        	row.createCell((short) 12).setCellValue((String) salary.getRewardIncentiveInformation());  
        }
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;filename="                + new String("客户经理绩效详情.xls".getBytes(), "iso-8859-1"));
        OutputStream out = response.getOutputStream();  
        wb.write(out);
        
        out.close();
	}
	
}
