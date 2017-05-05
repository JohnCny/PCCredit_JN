package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cardpay.pccredit.intopieces.model.ChatMessage;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface ChatMessageDao {
	
	public List<ChatMessage> findByApplicationId(@Param("applicationId")String applicationId);
	public int findCountByApplicationId(@Param("applicationId")String applicationId);
	
	
	
	public List<ChatMessage> findMsg(@Param("applicationId") String applicationId,@Param("page") int currentPage,@Param("limit") int limit);
	public List<ChatMessage> findMsg1(@Param("applicationId") String applicationId,@Param("page") int currentPage,@Param("limit") int limit);
	
	
}
