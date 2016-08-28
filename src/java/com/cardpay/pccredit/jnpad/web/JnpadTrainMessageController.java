package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.notification.constant.NotificationConstant;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.filter.NotificationMessageFilter;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadTrainMessageController {
	
	
	@Autowired
	private NotificationService notificationService;
	
	
/**
 * 培训通知
 * @param filter
 * @param request
 * @return
 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/NotifictionMessage/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse(@ModelAttribute NotificationMessageFilter filter, HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String userId= request.getParameter("userId");
		filter.setIsCheck(NotificationConstant.no_read);
		filter.setUserId(userId);
		filter.setNoticeType(NotificationEnum.peixun.toString());
		QueryResult<NotificationMessage> result = notificationService.findNotificationMessageByFilter(filter);
		map.put("result", result);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}
