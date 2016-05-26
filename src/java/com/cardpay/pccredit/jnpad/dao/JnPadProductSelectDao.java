package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.filter.JnpadProductFilter;
import com.cardpay.pccredit.jnpad.model.ProductAttribute;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnPadProductSelectDao {

	
	//通过ID查询数据库
	public  List<ProductAttribute> selectProductById(@Param("id") String id) ;
		// TODO Auto-generated method stub

	//查询产品数量
	public int selectProductsCount();

	public List<ProductAttribute> selectProductByFilter(JnpadProductFilter filter);



}

