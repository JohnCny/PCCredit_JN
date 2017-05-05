package com.cardpay.pccredit.websocket;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.cardpay.pccredit.intopieces.model.ChatMessage;
import com.cardpay.pccredit.manager.service.DailyReportScheduleService;
import com.cardpay.pccredit.nio.Global;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.spring.Beans;

/**
 * 处理TextWebSocketFrame
 */
@Service
@Scope("prototype")
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static final Logger logger = Logger.getLogger(TextWebSocketFrameHandler.class);
	@Autowired
	private Global global;
	
	
	@Autowired
	private CommonDao commonDao;
	
	public  static String displayName ="";;
	protected void channelRead0(ChannelHandlerContext ctx,TextWebSocketFrame msg) throws Exception { // (1)
		 Channel incoming = ctx.channel();
		 // 获取当前聊天时间
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String dateString = format.format(new Date());
	     
		 // 获取当前登陆聊天用户
		 String userId;
		 String message;
		 String appId;
		 String ptype;
		 
		 // get bean
		 DailyReportScheduleService dailyReportScheduleService =Beans.get(DailyReportScheduleService.class);
		 
		//文本
		 if(msg.text().indexOf("base64")==-1){
			 	if("000001".equals(msg.text().substring(0, 6))){
				    userId = msg.text().substring(0,6);
				    appId  = msg.text().substring(6, 38);
				    message = msg.text().substring(38, msg.text().length());
				 }else{
					userId = msg.text().substring(0, 32);
					appId  = msg.text().substring(32, 64);
					message = msg.text().substring(64, msg.text().length());
				 }
				 SystemUser loginUser=  dailyReportScheduleService.queryCustomer(userId);
				 displayName = loginUser.getDisplayName();
				 
			     // 存聊天记录
			  	 this.saveChatMessage(appId,displayName,"0",message.replaceAll(" ", ""),"","","");
			  	 
				 for (Channel channel : global.group) {
		            if (channel != incoming){
		            	channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+" " + message.replaceAll(" ", "")+" "+0+" "+"text"));
		            } else {
		            	channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+" " + message.replaceAll(" ", "")+" "+0+" "+"text"));
		            }
		         }
		 }else{//图片
			 System.out.println(msg.text());
			 if("000001".equals(msg.text().substring(0, 6))){
				    userId = msg.text().substring(0,6);
				    appId  = msg.text().substring(6, 38);
				    ptype  = msg.text().substring(44, 48);
				    message = msg.text().substring(48, msg.text().length());
				 }else{
					userId = msg.text().substring(0, 32);
					appId  = msg.text().substring(32, 64);
					ptype  = msg.text().substring(70, 74);
					message = msg.text().substring(74, msg.text().length());
				 }
			    
				 SystemUser loginUser=  dailyReportScheduleService.queryCustomer(userId);
				 displayName = loginUser.getDisplayName();
				 
			     // 存聊天记录
			  	 this.saveChatMessage(appId,displayName,"0","","",ptype,message);
			  	 
				 for (Channel channel : global.group) {
		            if (channel != incoming){
		            	channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+" " + message+" "+2+" "+ptype));
		            } else {
		            	channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+" " + message+" "+2+" "+ptype));
		            }
		         }
		 }
		
	}
	
   
	    
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        global.group.add(incoming);
        logger.info("Client:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        global.group.remove(incoming);
        logger.info("Client:"+incoming.remoteAddress()+"掉线");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
			throws Exception {
    	Channel incoming = ctx.channel();
    	logger.info("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
	}
	
	
	// 保存 聊天记录
	public void saveChatMessage(String appId,String userId,String type,String content,String url,String photoType,String photoBase){
		ChatMessage chatMessage = new ChatMessage();
		
		chatMessage.setApplicationId(appId);
		chatMessage.setCreatedBy(userId);
		chatMessage.setCreatedTime(new Date());
		chatMessage.setMsgType(type);
		chatMessage.setMsgContent(content);
		chatMessage.setResourceUrl(url);
		
		chatMessage.setPhotoBase(photoBase);
		chatMessage.setPhotoType(photoType);
		
		commonDao.insertObject(chatMessage);
	}

}
