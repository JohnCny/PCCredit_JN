package com.cardpay.pccredit.manager.dao;

import java.util.List;

import com.cardpay.pccredit.manager.filter.ManagerMeetingFilter;
import com.cardpay.pccredit.manager.model.ManagerMeetingInfo;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface ManagerMeetingDao {

	List<ManagerMeetingInfo> findManagerMeetingByFilter(ManagerMeetingFilter filter);

	int findManagerMeetingSizeByFilter(ManagerMeetingFilter filter);

}
