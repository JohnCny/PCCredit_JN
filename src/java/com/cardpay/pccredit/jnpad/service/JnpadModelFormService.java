package com.cardpay.pccredit.jnpad.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calcuation.enums.ModelType;
import com.cardpay.pccredit.creditEval.CommonDecisionModel;
import com.cardpay.pccredit.creditEvaluation.vo.CommonDecisionForm;
import com.cardpay.pccredit.intopieces.model.EvaResult;
import com.cardpay.pccredit.jnpad.filter.ModelFormFilter;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class JnpadModelFormService {
	@Autowired
	private CommonDao commonDao;
	
	
	public EvaResult saveEvaModelResult(CommonDecisionForm form){
		/**
		 * (信用-LNM00000000003  担保-LNM00000000004  抵押-LNM00000000001)
		 * CREDIT_CPY：信用-企业主模型
		 * CREDIT_INDI：信用-受薪者模型
		 * WARR_CPY：担保-企业主模型
		 * WARR_INDI：担保-受薪者模型
		 * COLLE_CPY：抵押企业主模型
		 * COLLE_INDI：抵押-受薪者模型
		 */
		/*if("0".equals(form.getLoanUse())){ 
			if("LNM00000000003".equals(form.getProdType())){
				ModelType.setModelType(ModelType.CREDIT_INDI);// 信用  消费
				Log.info("*********信用  消费*********");
			}else if("LNM00000000001".equals(form.getProdType())){
				ModelType.setModelType(ModelType.COLLE_INDI);// 抵押  消费
				Log.info("*********抵押  消费*********");
			}else{
				ModelType.setModelType(ModelType.WARR_INDI);// 担保  消费
				Log.info("*********担保  消费*********");
			}
		}else{
			if("LNM00000000003".equals(form.getProdType())){
				ModelType.setModelType(ModelType.CREDIT_CPY);// 信用  经营
				Log.info("*********信用 经营*********");
			}else if("LNM00000000001".equals(form.getProdType())){
				ModelType.setModelType(ModelType.COLLE_CPY);// 抵押  经营
				Log.info("*********抵押  经营*********");
			}else{
				ModelType.setModelType(ModelType.WARR_CPY);// 担保  经营
				Log.info("*********担保  经营*********");
			}					
		}*/
		if("CREDIT_CPY".equals(form.getModelType())){
			ModelType.setModelType(ModelType.CREDIT_CPY);
			Log.info("*********信用-企业主模型*********");
		}else if("CREDIT_INDI".equals(form.getModelType())){
			ModelType.setModelType(ModelType.CREDIT_INDI);
			Log.info("*********信用-受薪者模型*********");
		}else if("WARR_CPY".equals(form.getModelType())){
			ModelType.setModelType(ModelType.WARR_CPY);
			Log.info("*********担保-企业主模型*********");
		}else if("WARR_INDI".equals(form.getModelType())){
			ModelType.setModelType(ModelType.WARR_INDI);
			Log.info("*********担保-受薪者模型*********");
		}else if("COLLE_CPY".equals(form.getModelType())){
			ModelType.setModelType(ModelType.COLLE_CPY);
			Log.info("*********抵押企业主模型*********");
		}else if("COLLE_INDI".equals(form.getModelType())){
			ModelType.setModelType(ModelType.COLLE_INDI);
			Log.info("*********抵押-受薪者模型*********");
		}
		
		// model
		com.calcuation.model.JNCreditBusinessModel jnCreditBusinessModel = new com.calcuation.model.JNCreditBusinessModel(
				Double.valueOf(form.getApplyAmount()),
				Integer.parseInt(form.getLoanUse()),
			    Integer.parseInt(form.getSex()),
			    Integer.parseInt(form.getAge()),
			    Integer.parseInt(form.getEducation()),
			    Integer.parseInt(form.getResidence()),
			    Integer.parseInt(form.getMarriage()),
			    Integer.parseInt(form.getChildrenEducation()),
			    Integer.parseInt(form.getOwnedPropertyQuantity()),
			    Integer.parseInt(form.getMortgagePropertyQuantity()),
			    Double.valueOf(form.getMortgateBalance()), 				
			    Integer.parseInt(form.getOwnedCarsQuantity()), 			
			    Integer.parseInt(form.getBusinessYears()), 					
			    Integer.parseInt(form.getCredit()), 									
			    Integer.parseInt(form.getCreditCardOverdueCount()), 	
			    Integer.parseInt(form.getLoanOverdueCount()), 				
			    Double.valueOf(form.getLoanBalance()), 						
			    Double.valueOf(form.getMortgageRemaining()), 			
			    Integer.parseInt(form.getNumOfEconomicDependence()), 
				Double.valueOf(form.getLiquidAssents()), 					
				Double.valueOf(form.getStock()), 									
			    Double.valueOf(form.getFixedAssents()), 						
				Double.valueOf(form.getShortTermLiabilities()), 		
				Double.valueOf(form.getTotalLiabilities()), 				
				Double.valueOf(form.getTotalAssents()), 						
				Double.valueOf(form.getOwnersEquity()), 						
				Double.valueOf(form.getAnnualIncome()), 						
				Double.valueOf(form.getOtherIncome()),						
				Double.valueOf(form.getSpouseIncome()), 						
				Double.valueOf(form.getPaymentByPrivateUse()), 		
				Double.valueOf(form.getAnnualDisposableCapital()), 
				Integer.parseInt(form.getArticleCategory()), 				
				Double.valueOf(form.getCollateralValuation()),
				0);
		
		
		// save  model param
		this.saveModelParam(form);
		
		// delete by cardId
		String cardId  = form.getCardNo();
		this.deleteEvaResult(cardId);
		
		// Message
		com.calcuation.model.Message msg = jnCreditBusinessModel.getResult();
		
		// save eva_result
		EvaResult re = new EvaResult();
		re.setCname(form.getCname()); 
		re.setSex(form.getSex().equals("0")?"男":"女");
		re.setCardNo(form.getCardNo());
		re.setResult(msg.getResultStatus() == 1?"允许":"禁止");
		re.setMoney(msg.getBottom()+"-"+msg.getTop());
		re.setRefuseReason(msg.getFailureReason() == null? "" : msg.getFailureReason());
		re.setCreatedTime(new Date());
		re.setUserId(form.getUserId());
		re.setUserName(form.getUserName());
		this.saveEvaResult(re);
		
		return re;
	}
	
	public void saveModelParam(CommonDecisionForm form){
		try{
			CommonDecisionModel model = new CommonDecisionModel();
			
			model.setCustomerName(form.getCname());             
			model.setCardId(form.getCardNo());                   
			model.setExcelId(form.getExcelId());                  
			model.setApplyAmount(form.getApplyAmount());              
			model.setLoanUse(form.getLoanUse());                  
			model.setSex(form.getSex());                      
			model.setAge(form.getAge());                      
			model.setEducation(form.getEducation());                
			model.setResidence(form.getResidence());                
			model.setMarriage(form.getMarriage());                 
			model.setChildrenEducation(form.getChildrenEducation());        
			model.setOwnedPropertyQuantity(form.getOwnedPropertyQuantity());    
			model.setMortgagePropertyQuantity(form.getMortgagePropertyQuantity()); 
			model.setMortgateBalance(form.getMortgateBalance());          
			model.setOwnedCarsQuantity(form.getOwnedCarsQuantity());        
			model.setBusinessYears(form.getBusinessYears());            
			model.setCredit(form.getCredit());                   
			model.setCreditCardOverdueCount(form.getCreditCardOverdueCount());   
			model.setLoanOverdueCount(form.getLoanOverdueCount());         
			model.setLoanBalance(form.getLoanBalance());              
			model.setMortgageRemaining(form.getMortgageRemaining());        
			model.setNumOfEconomicDependence(form.getNumOfEconomicDependence());  
			model.setLiquidAssents(form.getLiquidAssents());            
			model.setStock(form.getStock());                    
			model.setFixedAssents(form.getFixedAssents());             
			model.setShortTermLiabilities(form.getShortTermLiabilities());     
			model.setTotalLiabilities(form.getTotalLiabilities());         
			model.setTotalAssents(form.getTotalAssents());             
			model.setOwnersEquity(form.getOwnersEquity());             
			model.setAnnualIncome(form.getAnnualIncome());             
			model.setOtherIncome(form.getOtherIncome());              
			model.setSpouseIncome(form.getSpouseIncome());             
			model.setPaymentByPrivateUse(form.getPaymentByPrivateUse());      
			model.setAnnualDisposableCapital(form.getAnnualDisposableCapital());  
			model.setArticleCategory(form.getArticleCategory());          
			model.setCollateralValuation(form.getCollateralValuation());      
			model.setModelType(form.getModelType());
			
			model.setCreatedTime(new Date());
		    commonDao.insertObject(model);
	  }catch(Exception e){
		  e.printStackTrace();
		  Log.error("保存四维授信模型参数错误!");
	  }
	}
	
	public void saveEvaResult(EvaResult result){
		commonDao.insertObject(result);
	}
	public void deleteEvaResult(String cardId){
		commonDao.queryBySql("delete from EVA_RESULT where card_No='"+cardId+"'", null);
	}
	public List<EvaResult> getEvaModelResult(String userId){
		String sql="select * from EVA_RESULT ";
		if(userId!=null&&userId!=""){
			sql+=" where user_Id='"+userId+"'";
		}
		sql+=" ORDER BY created_time desc";
		return commonDao.queryBySql(EvaResult.class, sql, null);
	}

	public QueryResult<EvaResult> getEvaModelResultPC(ModelFormFilter filter) {
		String sql="select * from EVA_RESULT ";
		HashMap<String, Object> params=new HashMap<String, Object>();
		if(filter.getUserId()!=null&&filter.getUserId()!=""){
			sql+=" where user_Id=#{userId}";
		params.put("userId", filter.getUserId());
		}
		sql+=" ORDER BY created_time desc";
		QueryResult<EvaResult> list= commonDao.queryBySqlInPagination(EvaResult.class, sql, params, filter.getStart(), filter.getLimit());
		return list;
	}
}
