package com.cardpay.pccredit.jnpad.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.jnpad.dao.JnpadCustomerInfoInsertDao;
import com.cardpay.pccredit.jnpad.model.CustomerBank;
import com.cardpay.pccredit.jnpad.model.CustomerCarInfo;
import com.cardpay.pccredit.jnpad.model.CustomerCompanyBusiness;
import com.cardpay.pccredit.jnpad.model.CustomerCompanyInfo;
import com.cardpay.pccredit.jnpad.model.CustomerContact;
import com.cardpay.pccredit.jnpad.model.CustomerFamilyInfo;
import com.cardpay.pccredit.jnpad.model.CustomerHouse;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.CustomerLiving;
import com.cardpay.pccredit.jnpad.model.CustomerPersonal;
import com.cardpay.pccredit.jnpad.model.CustomerStore;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class JnpadCustomerInfoInsertServ‎ice {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	@Autowired
	private JnpadCustomerInfoInsertDao jnpadcustomerinfoinsertdao;
	
//插入客户信息
	public String customerInforInsert(CustomerInfo customerinfor) {
		
			String id = IDGenerator.generateID();
			customerinfor.setId(id);
			customerinfor.setCreatedTime(new Date());
			commonDao.insertObject(customerinfor);
			return customerinfor.getId();
		}
	
	
	
	public QueryResult<IntoPieces> findintoPiecesByFilter(
			IntoPiecesFilter filter) {
		int sum = intoPiecesComdao.findintoPiecesByFilterCount(filter);
		QueryResult<IntoPieces> queryResult = this.findintoPiecesInfoByFilter(filter,sum);
		QueryResult<IntoPieces> qs = new QueryResult<IntoPieces>(sum, queryResult.getItems());
		List<IntoPieces> intoPieces = qs.getItems();
		for(IntoPieces pieces : intoPieces){
			if(pieces.getStatus()==null){
				pieces.setNodeName("未提交申请");
			}
			else{
				if(pieces.getStatus().equals(Constant.SAVE_INTOPICES)){
					pieces.setNodeName("未提交申请");
				}else if(pieces.getStatus().equals(Constant.APPROVE_INTOPICES)){
					//String nodeName = intoPiecesComdao.findAprroveProgress(pieces.getId());
					String nodeName = intoPiecesComdao.findNodeName(pieces.getId());
					if(StringUtils.isNotEmpty(nodeName)){
						pieces.setNodeName(nodeName);
					} else {
						pieces.setNodeName("不在审批中");
					}
				}else if(pieces.getStatus().equals(Constant.REFUSE_INTOPICES)||pieces.getStatus().equals("returnedToFirst")){
					List<HashMap<String, Object>> list = intoPiecesComdao.findNodeNameJN(pieces.getId());
					String refusqlReason ="";
					String fallBackReason ="";
					if(list != null && list.size() > 0){
						HashMap<String, Object> map = list.get(0);
						refusqlReason = (String) map.get("REFUSAL_REASON");
						fallBackReason =(String) map.get("FALLBACK_REASON");
					}
					pieces.setNodeName("审批结束");
					pieces.setRefusqlReason(refusqlReason);
					pieces.setFallBackReason(fallBackReason);
				}else {
					pieces.setNodeName("审批结束");
				}
			}
		}
		return qs;
	}
	
	public QueryResult<IntoPieces> findintoPiecesInfoByFilter(
			IntoPiecesFilter filter,int sum) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String id = filter.getId();
		String chineseName  = filter.getChineseName();
		String productName = filter.getProductName();
		String userId = filter.getUserId();
		String cardId = filter.getCardId();
		String status = filter.getStatus();
		params.put("userId", userId);
		StringBuffer sql = new StringBuffer("select t.id,t.customer_id,b.ty_customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.final_approval,t.status,t.CREATED_TIME,t.ACTUAL_QUOTE as reqlmt  from customer_application_info t,basic_customer_information b,product_attribute p where t.customer_id=b.id  and t.product_id=p.id  ");
		if(StringUtils.trimToNull(userId)!=null){
			sql.append("and b.user_id = #{userId}");
		}
		
//		if(StringUtils.trimToNull(filter.getStartAmt())!=null){
//			params.put("startAmt", filter.getStartAmt());
//			sql.append("and t.APPLY_QUOTA >= #{startAmt}");
//		}
//		
//		if(StringUtils.trimToNull(filter.getEndAmt())!=null){
//			params.put("endAmt", filter.getEndAmt());
//			sql.append("and t.APPLY_QUOTA <= #{endAmt}");
//		}
		
		String custManagerIds = filter.getCustManagerIds();
		if(StringUtils.trimToNull(custManagerIds)!=null){
			sql.append("and b.user_id in ");
			sql.append(custManagerIds);
		}
		
		if(StringUtils.trimToNull(productName)!=null){
			params.put("productName", productName);
			 sql.append(" and p.product_name like '%'||#{productName}||'%' ");
			}
		
		if(StringUtils.trimToNull(id)!=null){
			params.put("id", id);
			sql.append(" and t.id like '%'||#{id}||'%' ");
			}
		if(StringUtils.trimToNull(status)!=null){
			params.put("status", status);
			sql.append("and t.status= #{status}");
			}
		if(StringUtils.trimToNull(cardId)!=null||StringUtils.trimToNull(chineseName)!=null){
			if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)!=null){
			    //sql.append(" and (b.card_id like '%"+cardId+"%' or b.chinese_name like '%"+chineseName+"%' )");
			    sql.append(" and b.card_id like '%"+cardId+"%' and b.chinese_name like '%"+chineseName+"%' ");
			}else if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)==null){
				params.put("cardId", cardId);
				sql.append(" and b.card_id like '%'||#{cardId}||'%' ");
			}else if(StringUtils.trimToNull(cardId)==null&&StringUtils.trimToNull(chineseName)!=null){
				params.put("chineseName", chineseName);
				sql.append(" and b.chinese_name like '%'||#{chineseName}||'%' ");
			}
		}
		
		sql.append(" order by t.id asc");
		return commonDao.queryBySqlInPagination(IntoPieces.class, sql.toString(), params,
				filter.getStart(),sum);
	}



	public void insertCustomerInfoGr(CustomerPersonal customerpersonal) {
		String id = IDGenerator.generateID();
		customerpersonal.setId(id);
		customerpersonal.setCreatedate(new Date());;
		commonDao.insertObject(customerpersonal);
	}



	public void updatetCustomerInfoGr(CustomerPersonal customerpersonal) {

		commonDao.updateObject(customerpersonal);
	}



	public List<CustomerPersonal> selectCustomerInfoGr(String customerId) {
		String sql="select * from customerinformation_gr where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerPersonal.class, sql, params);
	}



	public void insertCustomerInfoFc(CustomerHouse customerhouse) {
		// TODO Auto-generated method stub
		String id = IDGenerator.generateID();
		customerhouse.setId(id);
		commonDao.insertObject(customerhouse);
	}
	
	////==================================================================
	public void updateCustomerInfoFc(CustomerHouse customerhouse) {
		// TODO Auto-generated method stub
		commonDao.updateObject(customerhouse);
	}
	
	public void updateCustomerCarInfo(CustomerCarInfo customerCarInfo) {
		// TODO Auto-generated method stub
		commonDao.updateObject(customerCarInfo);
	}
	
	public void updateCustomerLxrInfo(CustomerContact customerContact) {
		// TODO Auto-generated method stub
		commonDao.updateObject(customerContact);
	}
	
	public void updateCustomerQykhInfo(CustomerBank customerBank) {
		// TODO Auto-generated method stub
		commonDao.updateObject(customerBank);
	}

	public List<CustomerHouse> selectCustomerInfoFcById(String id) {
		String sql="select * from customerinformation_fc where id='"+id+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerHouse.class, sql, params);
	}
	
	public List<CustomerCarInfo> selectCustomerInfoccById(String id) {
		String sql="select * from customerinformation_cc where id='"+id+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerCarInfo.class, sql, params);
	}
	
	public List<CustomerContact> selectCustomerInfolxrById(String id) {
		String sql="select * from customerinformation_lxr where id='"+id+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerContact.class, sql, params);
	}
	
	public List<CustomerBank> selectCustomerInfoQykhById(String id) {
		String sql="select * from customerinformation_qykh where id='"+id+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerBank.class, sql, params);
	}
	
////==================================================================


	public List<CustomerHouse> selectCustomerInfoFc(String customerId) {
		String sql="select * from customerinformation_fc where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerHouse.class, sql, params);
	}



	public List<CustomerFamilyInfo> selectCustomerInfoJt(String customerId) {
		String sql="select * from customerinformation_jt where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerFamilyInfo.class, sql, params);
	}



	public void updatetCustomerInfoJt(CustomerFamilyInfo customerfamilyinfo) {
		commonDao.updateObject(customerfamilyinfo);
		
	}



	public void insertCustomerInfoJt(CustomerFamilyInfo customerfamilyinfo) {
		String id = IDGenerator.generateID();
		customerfamilyinfo.setId(id);
		customerfamilyinfo.setCreateDate(new Date());;
		commonDao.insertObject(customerfamilyinfo);
		
	}



	public List<CustomerCarInfo> selectCustomerInfoCc(String customerId) {
		String sql="select * from customerinformation_cc where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerCarInfo.class, sql, params);
	}



	public void insertCustomerInfoCc(CustomerCarInfo customercarinfo) {
		String id = IDGenerator.generateID();
		customercarinfo.setId(id);
		customercarinfo.setCreateDate(new Date());;
		commonDao.insertObject(customercarinfo);
	}



	public List<CustomerContact> selectCustomerInfoLxr(String customerId) {
		String sql="select * from customerinformation_lxr where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerContact.class, sql, params);
	}



	public void insertCustomerInfoLxr(CustomerContact customercontact) {
		String id = IDGenerator.generateID();
		customercontact.setId(id);
		customercontact.setCreateDate(new Date());;
		commonDao.insertObject(customercontact);
		
	}



	public List<CustomerLiving> selectCustomerInfoJz(String customerId) {
		String sql="select * from customerinformation_jz where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerLiving.class, sql, params);
	}



	public void updatetCustomerInfoJz(CustomerLiving customerliving) {
		
		commonDao.updateObject(customerliving);
	}



	public void insertCustomerInfoJz(CustomerLiving customerliving) {
		
		String id = IDGenerator.generateID();
		customerliving.setId(id);
		customerliving.setCreateDate(new Date());;
		commonDao.insertObject(customerliving);
	}



	public void updatetCustomerInfoQyxx(CustomerCompanyInfo customercompanyinfo) {
		commonDao.updateObject(customercompanyinfo);
		
	}



	public void insertCustomerInfoQyxx(CustomerCompanyInfo customercompanyinfo) {
		String id = IDGenerator.generateID();
		customercompanyinfo.setId(id);
		customercompanyinfo.setCreateDate(new Date());;
		commonDao.insertObject(customercompanyinfo);
	}



	public List<CustomerCompanyInfo> selectCustomerInfoQyxx(String customerId) {
		String sql="select * from customerinformation_qyxx where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerCompanyInfo.class, sql, params);
	}



	public List<CustomerCompanyBusiness> selectCustomerInfoQyyw(String customerId) {
		String sql="select * from customerinformation_qyyw where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerCompanyBusiness.class, sql, params);
	}



	public void updatetCustomerInfoQyyw(CustomerCompanyBusiness customercompanybusiness) {
		commonDao.updateObject(customercompanybusiness);
		
	}



	public void insertCustomerInfoQyyw(CustomerCompanyBusiness customercompanybusiness) {
		String id = IDGenerator.generateID();
		customercompanybusiness.setId(id);
		customercompanybusiness.setCreateDate(new Date());;
		commonDao.insertObject(customercompanybusiness);
		
	}



	public List<CustomerStore> selectCustomerInfoQydp(String customerId) {
		String sql="select * from customerinformation_qydp where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerStore.class, sql, params);
	}



	public void updatetCustomerInfoQydp(CustomerStore customerstore) {
		commonDao.updateObject(customerstore);
		
	}



	public void insertCustomerInfoQydp(CustomerStore customerstore) {
		String id = IDGenerator.generateID();
		customerstore.setId(id);
		customerstore.setCreateDate(new Date());;
		commonDao.insertObject(customerstore);
		
	}



	public void insertCustomerInfoQykh(CustomerBank customerbank) {
		String id = IDGenerator.generateID();
		customerbank.setId(id);
		customerbank.setCreateDate(new Date());;
		commonDao.insertObject(customerbank);
	}



	public List<CustomerBank> selectCustomerInfoQykh(String customerId) {
		String sql="select * from customerinformation_qykh where customer_Id='"+customerId+"'";
		HashMap<String, Object> params = new HashMap<String, Object>();
		return commonDao.queryBySql(CustomerBank.class, sql, params);
	}



	public void deleteinfo(String id, String tables) {
		jnpadcustomerinfoinsertdao.deleteinfo(id,tables);
	}
	
	}


