package com.cardpay.pccredit.jnpad.dao;

import org.apache.ibatis.annotations.Param;

import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadCustomerInfoInsertDao {

	public void deleteinfo(@Param(value = "id")String id,@Param(value = "tables") String tables);
		
}
