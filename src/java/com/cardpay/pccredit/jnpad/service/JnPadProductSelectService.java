package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnPadProductSelectDao;
import com.cardpay.pccredit.jnpad.filter.JnpadProductFilter;
import com.cardpay.pccredit.jnpad.model.ProductAttribute;
/**
 * pad查询service
 * @author sealy
 *
 */
@Service
public class JnPadProductSelectService {
	@Autowired
	private JnPadProductSelectDao jnPadProductSelectDao;
	
	/**
	 * 查ID
	 * @param id
	 * @return
	 */
	public List<ProductAttribute> selectProductById(String id) {
		
		return jnPadProductSelectDao.selectProductById(id);
	}

	public int selectProductsCount() {
		// TODO Auto-generated method stub
		return jnPadProductSelectDao.selectProductsCount();
	}
/**
 * 过滤条件查询
 * @param filter
 * @return
 */
	public List<ProductAttribute> selectProductByFilter(JnpadProductFilter filter) {
		
		return jnPadProductSelectDao.selectProductByFilter(filter);
	}

}


