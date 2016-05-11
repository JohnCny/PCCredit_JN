package com.cardpay.pccredit.jnpad.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.jnpad.dao.JnIpadCustAppInfoXxDao;
import com.cardpay.pccredit.jnpad.filter.CustomerApprovedFilter;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class JnIpadCustAppInfoXxService {
	
	@Autowired
	private JnIpadCustAppInfoXxDao jnIpadCustAppInfoDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	public int findCustAppInfoXxCount(String userId,String status1,String status2,String status3,String status4){
		return jnIpadCustAppInfoDao.findCustAppInfoXxCount(userId,status1,status2,status3,status4);
	}
	
	
	public List<IntoPieces> findCustomerApprovedList(CustomerApprovedFilter filter) {
		List<IntoPieces>  intoPieces = jnIpadCustAppInfoDao.findCustomerApprovedList(filter);
		for(IntoPieces pieces : intoPieces){
			if(pieces.getStatus()==null){
				pieces.setNodeName("未提交申请");
			}
			else{
				if(pieces.getStatus().equals(Constant.SAVE_INTOPICES)){
					pieces.setNodeName("未提交申请");
				}else if(pieces.getStatus().equals(Constant.APPROVE_INTOPICES)){
					String nodeName = intoPiecesComdao.findNodeName(pieces.getId());
					if(StringUtils.isNotEmpty(nodeName)){
						pieces.setNodeName(nodeName);
					} else {
						pieces.setNodeName("不在审批中");
					}
				}else {
					pieces.setNodeName("审批结束");
				}
			}
		}
		return intoPieces;
	}
	
	public int findCustomerApprovedCountList(CustomerApprovedFilter filter) {
		return jnIpadCustAppInfoDao.findCustomerApprovedCountList(filter);
	}
	
	public Map<String,Object> updateCustomerApplicationInfo(CustomerApprovedFilter filter){
	   Map<String,Object> map = new LinkedHashMap<String,Object>();
       int i = jnIpadCustAppInfoDao.updateCustomerApplicationInfo(filter);
       if(i>0){
    	   map.put("result", "fail");
       }else{
    	   map.put("result", "success");
       }
       return map;
	}
	
	
	public String calCreditAmt(CustomerApprovedFilter filter){
		return jnIpadCustAppInfoDao.calCreditAmt(filter);
	}
	
	public int overdueCustomerCount(CustomerApprovedFilter filter){
		return jnIpadCustAppInfoDao.overdueCustomerCount(filter);
	}
}
