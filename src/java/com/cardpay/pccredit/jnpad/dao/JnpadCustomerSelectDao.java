package com.cardpay.pccredit.jnpad.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.CIPERSONBASINFO;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadCustomerSelectDao {

	
	//根据证件号码查客户信息
	public CustomerInfo selectCustomerInforByCardId(@Param(value = "cardId") String cardId);

	//根据id查客户信息
	public CIPERSONBASINFO selectCustomerInfoById(@Param(value = "id")String id);

	public CIPERSONBASINFO selectCustomerByNm(@Param(value = "custid")String custid);





}