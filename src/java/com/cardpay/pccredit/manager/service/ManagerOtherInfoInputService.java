package com.cardpay.pccredit.manager.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calcuation.enums.ModelType;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.creditEval.CommonDecisionModel;
import com.cardpay.pccredit.creditEvaluation.vo.CommonDecisionForm;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.dao.IntoPiecesDao;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLogForm;
import com.cardpay.pccredit.intopieces.model.ApplicationDataImport;
import com.cardpay.pccredit.intopieces.model.BasicCustomerInformationS;
import com.cardpay.pccredit.intopieces.model.ChatMessage;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerCareersInformationS;
import com.cardpay.pccredit.intopieces.model.CustomerCreditInfo;
import com.cardpay.pccredit.intopieces.model.EvaResult;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.web.ApproveHistoryForm;
import com.cardpay.pccredit.manager.dao.ManagerOtherInfoInputDao;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.LoanApproved;
import com.cardpay.pccredit.manager.model.LoanRefused;
import com.cardpay.pccredit.manager.model.ManagerBelongMap;
import com.cardpay.pccredit.postLoan.model.MibusidataForm;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.model.SystemUser;
import com.jcraft.jsch.Logger;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class ManagerOtherInfoInputService {

	// TODO 路径使用相对路径，首先获得应用所在路径，之后建立上传文件目录，图片类型使用IMG，文件使用DOC

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerOtherInfoInputDao managerOtherInfoInputDao;

	
	/**
	 * 放款台账
	 */
	public QueryResult<LoanApproved> findLoanApprovedByFilter(DimensionalFilter filter) {
		List<LoanApproved> pList = managerOtherInfoInputDao.findLoanApprovedByFilter(filter);
		int size = managerOtherInfoInputDao.findLoanApprovedCountByFilter(filter);
		QueryResult<LoanApproved> queryResult = new QueryResult<LoanApproved>(size, pList);
		return queryResult;
	}
	
	public void insertLoanApproved(LoanApproved loanApproved){
		commonDao.insertObject(loanApproved);
	}
	
	public void updateLoanApproved(LoanApproved loanApproved){
		commonDao.updateObject(loanApproved);
	}
	
	public LoanApproved findLoanApprovedById(String id){
		return commonDao.findObjectById(LoanApproved.class, id);
	}
	/**
	 * @param 拒绝台账
	 */
	public QueryResult<LoanRefused> findLoanRefusedByFilter(DimensionalFilter filter) {
		List<LoanRefused> pList = managerOtherInfoInputDao.findLoanRefusedByFilter(filter);
		int size = managerOtherInfoInputDao.findLoanRefusedCountByFilter(filter);
		QueryResult<LoanRefused> queryResult = new QueryResult<LoanRefused>(size, pList);
		return queryResult;
	}

	public void insertLoanRefused(LoanRefused loanRefused) {
		commonDao.insertObject(loanRefused);
	}

	public void updateLoanRefused(LoanRefused loanRefused) {
		commonDao.updateObject(loanRefused);
	}
	
	public LoanRefused findLoanRefusedById(String id) {
		return commonDao.findObjectById(LoanRefused.class, id);
	}
	
	
    public void saveLoadRefuseAndUpdateApplyState(LoanRefused loanRefused){
    	// 保存放款台账信息表
    	this.insertLoanRefused(loanRefused);
		// 修改申请表状态 15-款拒绝
    	String sql = "update APPLYSTANDINGBOOK set state = '15' where id='"+loanRefused.getAppId()+"'";
    	commonDao.queryBySql(sql, null);
    }
	
	
	
}
