package com.cardpay.pccredit.jnpad.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.modules.privilege.dao.AccessRightMapper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.encrypt.EncryptHelper;

@Service
public class JnIpadUserLoginDao {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AccessRightMapper accessRightMapper;
	
	public User find(String login, String password){
		User user = accessRightMapper.authUserByLogin(login);
		if(user != null){
			if(EncryptHelper.md5(password).equals(user.getPassword())){
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
