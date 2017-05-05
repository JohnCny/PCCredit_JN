package com.cardpay.pccredit.websocket;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	@Autowired
	private Global global;
	
	
	@Autowired
	private CommonDao commonDao;
	
	public  static String displayName ="";;
	protected void channelRead0(ChannelHandlerContext ctx,TextWebSocketFrame msg) throws Exception { // (1)
		 Channel incoming = ctx.channel();
		 // 获取当前登陆聊天用户
		 String userId;
		 String message;
		 String appId;
		 if("000001".equals(msg.text().substring(0, 6))){
		    userId = msg.text().substring(0,6);
		    appId  = msg.text().substring(6, 38);
		    message = msg.text().substring(38, msg.text().length());
		 }else{
			userId = msg.text().substring(0, 32);
			appId  = msg.text().substring(32, 64);
			message = msg.text().substring(64, msg.text().length());
		 }
	     DailyReportScheduleService dailyReportScheduleService =Beans.get(DailyReportScheduleService.class);
		 SystemUser loginUser=  dailyReportScheduleService.queryCustomer(userId);
		 displayName = loginUser.getDisplayName();
		 
		 // 获取当前聊天时间
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String dateString = format.format(new Date());
		for (Channel channel : global.group) {
			
			// 存聊天记录
			this.saveChatMessage(appId,displayName,"0",message.replaceAll(" ", ""),"");
			
            if (channel != incoming){
            	//channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+":\n" + message));
            	channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+" " + message.replaceAll(" ", "")));
            } else {
            	//channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+":\n" + message));
            	channel.writeAndFlush(new TextWebSocketFrame(loginUser.getDisplayName()+" "+dateString+" " + message.replaceAll(" ", "")));
            }
        }
	}
	
   /* public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
         Channel incoming = ctx.channel();
         channels.writeAndFlush(new TextWebSocketFrame("[注意] " + incoming.remoteAddress() + " 加入群聊"));
        channels.add(incoming);
		System.out.println("Client:"+incoming.remoteAddress() +"加入");
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
         Channel incoming = ctx.channel();
         channels.writeAndFlush(new TextWebSocketFrame("[注意]  " + incoming.remoteAddress() + " 离开群聊"));
		 System.out.println("Client:"+incoming.remoteAddress() +"离开");
    }*/
	    
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        global.group.add(incoming);
		System.out.println("Client:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        global.group.remove(incoming);
		System.out.println("Client:"+incoming.remoteAddress()+"掉线");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
			throws Exception {
    	Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
	}
	
	
	// 保存 聊天记录
	public void saveChatMessage(String appId,String userId,String type,String content,String url){
		ChatMessage chatMessage = new ChatMessage();
		
		chatMessage.setApplicationId(appId);
		chatMessage.setCreatedBy(userId);
		chatMessage.setCreatedTime(new Date());
		chatMessage.setMsgType(type);
		chatMessage.setMsgContent(content);
		chatMessage.setResourceUrl(url);
		
		commonDao.insertObject(chatMessage);
	}

}
