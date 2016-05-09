package com.cardpay.pccredit.jnpad.dao;

import org.apache.ibatis.annotations.Param;

import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnIpadCustAppInfoXxDao {

    public int findCustomerApplicationInfoCount(@Param("userId") String userId,
									    		@Param("status1") String status,
									    		@Param("status2") String status2,
									    		@Param("status3") String status3,
									    		@Param("status4") String status4);

}
