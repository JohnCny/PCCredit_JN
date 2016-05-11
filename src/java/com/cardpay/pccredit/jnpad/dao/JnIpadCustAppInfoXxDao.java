package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.jnpad.filter.CustomerApprovedFilter;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnIpadCustAppInfoXxDao {

    public int findCustAppInfoXxCount(@Param("userId") String userId,
						    		  @Param("status1") String status,
						    		  @Param("status2") String status2,
						    		  @Param("status3") String status3,
						    		  @Param("status4") String status4);
    
    
    
    public List<IntoPieces> findCustomerApprovedList(CustomerApprovedFilter filter);
	
	public int findCustomerApprovedCountList(CustomerApprovedFilter filter);
	
	public int updateCustomerApplicationInfo(CustomerApprovedFilter filter);
	
	public String calCreditAmt(CustomerApprovedFilter filter);
	
	public int overdueCustomerCount(CustomerApprovedFilter filter);
	
}
