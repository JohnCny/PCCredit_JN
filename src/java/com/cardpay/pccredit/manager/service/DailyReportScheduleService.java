package com.cardpay.pccredit.manager.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cardpay.pccredit.manager.model.DailyAccountManager;
import com.cardpay.pccredit.manager.model.WeeklyAccountManager;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.date.DateHelper;

/**
 * 客户经理日报
 * 2016-03-18 下午2:09:26
 */
@Service
public class DailyReportScheduleService {
	public Logger log = Logger.getLogger(DailyReportScheduleService.class);
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;

	@Autowired
	private WeeklyAccountService weeklyAccountService;
	
	@Autowired
	private DailyAccountService dailyAccountService;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	PlatformTransactionManager txManager;
	
	
	
	/**
	 * 客户经理日报
	 * 周六或周日生成下周的日报
	 */
	public void insertWeekSchedule(){
	    log.info("【客户经理日报生成start】"+new Date()+"***********************************************");
		try{
			  Calendar nextdate=Calendar.getInstance(Locale.CHINA);
			  //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			  //Date date = sdf.parse("20160320");
			  nextdate.setTime(new Date());
			  nextdate.add(Calendar.WEEK_OF_MONTH, 1);
			  nextdate.set(Calendar.DAY_OF_WEEK, nextdate.getActualMinimum(Calendar.DAY_OF_WEEK));
			  nextdate.add(Calendar.DATE, 1);
			  //startDate 
			  String startDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
			  nextdate.add(Calendar.DATE, 4);
			  //endDate
			  String endDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
			/**
			 * 查找所有客户经理
			 */
			List<AccountManagerParameterForm> managerList=accountManagerParameterService.findAccountManagerParameterAll();
			for(AccountManagerParameterForm accountManagerParameterForm:managerList){
				String title=accountManagerParameterForm.getDisplayName()+"("+startDate+"到"+endDate+")周报";
				WeeklyAccountManager weeklyAccountManager=new WeeklyAccountManager();
				weeklyAccountManager.setCustomerManagerId(accountManagerParameterForm.getUserId());
				weeklyAccountManager.setTitle(title);
				weeklyAccountManager.setCreatedTime(new Date());
				weeklyAccountManager.setModifiedTime(new Date());
				weeklyAccountService.insertWeeklyAccount(weeklyAccountManager);
				for(int i=1;i<=5;i++){
					DailyAccountManager dailyAccountManager=new DailyAccountManager();
					dailyAccountManager.setWeeklyId(weeklyAccountManager.getId());
					dailyAccountManager.setWhatDay(i);
					dailyAccountManager.setCreatedTime(new Date());
					dailyAccountManager.setModifiedTime(new Date());
					dailyAccountService.insertDailyAccount(dailyAccountManager);
				}
			}
			//upd task
			accountManagerParameterService.updBatchTaskFlow("100","rb");
		}catch(Exception e){
			e.printStackTrace();
			this.updBtachtask("001","rb");
			throw new RuntimeException(e);
		}
		log.info("【客户经理日报生成end】"+new Date()+"***********************************************");
	}
	
	//upd
		public void updBtachtask(String status,String batchCode){
			DefaultTransactionDefinition  transStatus  = new DefaultTransactionDefinition();
			transStatus.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			TransactionStatus one = txManager.getTransaction(transStatus);
			try{
				//upd task
				accountManagerParameterService.updBatchTaskFlow(status,batchCode);
				txManager.commit(one);
			}catch (Exception e){
				txManager.rollback(one);
			}
		}
	
	
	
	/**
	 * 每日生成task,日终将数据移到历史表里
	 * 
	 */
	public void doTransferData(){
		log.info("【批处理task生成start】"+new Date()+"***********************************************");
		//将status-100数据移到历史表t_batch_task_log 
		accountManagerParameterService.insertBatchTaskLogFlow();
		
		//清空表数据
		String sql = " truncate   table   t_batch_task";
		commonDao.queryBySql(sql, null);
		
		//task
		//accountManagerParameterService.insertBatchTaskFlow("rb","日报");//初始
		log.info("【批处理task生成end】"+new Date()+"***********************************************");
	}
	
	
}
