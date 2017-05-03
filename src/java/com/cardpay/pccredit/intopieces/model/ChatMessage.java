/**
 * 
 */
package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 聊天记录
 */
@ModelParam(table = "CHAT_MESSAGE")
public class ChatMessage extends BusinessModel{

	private static final long serialVersionUID = 1L;
	
	private String applicationId; 
	private String msgType;
	private String msgContent;
	private String resourceUrl;
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	
}
