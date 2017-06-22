package com.cardpay.pccredit.nio;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.model.ChatMessage;
import com.cardpay.pccredit.manager.service.DailyReportScheduleService;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.util.spring.Beans;

@Service
@Scope("prototype")
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {
	private static final Logger logger = Logger
			.getLogger(TcpServerHandler.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private WebSocketServerHandshaker handshaker;

	@Autowired
	private Global global;

	String appId;
	String userId;
	String msgType;
	String pType;

	// 本次请求的 code
	private static final String HTTP_REQUEST_APPID = "appId";
	private static final String HTTP_REQUEST_USERID = "userId";
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.info("客户端与服务端连接开启");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		// 移除
		// 查询客户对应appid
		if (!global.loginClientMap
				.containsKey(ctx.channel().id().asShortText())) {
			return;
		}
		String appId = global.loginClientMap.get(ctx.channel().id()
				.asShortText());
		global.channelGroupMap.get(appId).remove(ctx.channel());
		logger.info("客户端与服务端连接关闭");
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// 传统的HTTP接入
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		}
		// WebSocket接入
		else if (msg instanceof TextWebSocketFrame) {
			handleWebSocketFrame(ctx, (TextWebSocketFrame) msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private void handleHttpRequest(ChannelHandlerContext ctx,
			FullHttpRequest req) throws Exception {
		
		// 如果HTTP解码失败，返回HHTP异常
		if (!req.decoderResult().isSuccess()
				|| (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1,
					BAD_REQUEST));
			return;
		}

		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
				req.uri());
		Map<String, List<String>> parameters = queryStringDecoder.parameters();

		if (parameters.size() == 0
				|| !parameters.containsKey(HTTP_REQUEST_APPID)
				|| !parameters.containsKey(HTTP_REQUEST_USERID)) {
			// System.err.printf("参数不可缺省");
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1,
					NOT_FOUND));
			return;
		}

		// 房间列表中如果不存在则为该频道,则新增一个频道 ChannelGroup
		appId = parameters.get(HTTP_REQUEST_APPID).get(0);
		userId = parameters.get(HTTP_REQUEST_USERID).get(0);

		if (!global.channelGroupMap.containsKey(appId)) {
			global.channelGroupMap.put(appId, new DefaultChannelGroup(
					GlobalEventExecutor.INSTANCE));
		}

		// 添加
		global.channelGroupMap.get(appId).add(ctx.channel());

		// 新建channelid和appid的对应关系
		global.loginClientMap.put(ctx.channel().id().asShortText(), appId);
		
		// 构造握手响应返回,本机测试
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://61.34.0.31:" + Constant.WS_PORT + "/ws", null, true,
				1389101);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx
					.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx,
			TextWebSocketFrame frame) {
		// 获取当前聊天时间
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format(new Date());

		// get bean
		DailyReportScheduleService dailyReportScheduleService = Beans
				.get(DailyReportScheduleService.class);

		// 获取当前登陆聊天用户
		String message;

		TextWebSocketFrame msg = (TextWebSocketFrame) frame;
		Channel incoming = ctx.channel();

		//appId = global.loginClientMap.get(incoming.id().asShortText());
		// 文本
		if (msg.text().indexOf("base64") == -1) {
			message = msg.text();
			SystemUser loginUser = dailyReportScheduleService
					.queryCustomer(userId);

			// 存聊天记录
			// String id = IDGenerator.generateID();
			String id = this.saveChatMessage(appId, loginUser.getDisplayName(),
					"0", message.replaceAll(" ", ""), "", "", "");

			// 查询客户对应appid
			if (!global.loginClientMap.containsKey(incoming.id().asShortText())) {
				return;
			}

			if (global.channelGroupMap.containsKey(appId)) {
				ChannelGroup group = global.channelGroupMap.get(appId);
				for (Channel channel : group) {
					if (channel != incoming) {
						channel.writeAndFlush(new TextWebSocketFrame(loginUser
								.getDisplayName()
								+ " "
								+ dateString
								+ " "
								+ message.replaceAll(" ", "")
								+ " "
								+ 0
								+ " "
								+ "text"));
					} else {
						channel.writeAndFlush(new TextWebSocketFrame(loginUser
								.getDisplayName()
								+ " "
								+ dateString
								+ " "
								+ message.replaceAll(" ", "")
								+ " "
								+ 0
								+ " "
								+ "text"));
					}
				}
			}
		} else {// 图片
			message = msg.text();
			SystemUser loginUser = dailyReportScheduleService
					.queryCustomer(userId);
			
			// 解码后图片存放路径
			String path = GenerateImage(message, pType);
			// String id = IDGenerator.generateID();
			// 存聊天记录
			String id = this.saveChatMessage(appId, loginUser.getDisplayName(),
					"1", "", path, pType, message);

			if (global.channelGroupMap.containsKey(appId)) {
				ChannelGroup group = global.channelGroupMap.get(appId);
				for (Channel channel : group) {
					if (channel != incoming) {
						channel.writeAndFlush(new TextWebSocketFrame(loginUser
								.getDisplayName()
								+ " "
								+ dateString
								+ " "
								+ null
								+ " " + 2 + " " + pType + " " + id));
					} else {
						channel.writeAndFlush(new TextWebSocketFrame(loginUser
								.getDisplayName()
								+ " "
								+ dateString
								+ " "
								+ null
								+ " " + 2 + " " + pType + " " + id));
					}
				}
			}
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, FullHttpResponse res) {
		// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
					CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(res, res.content().readableBytes());
		}

		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	// 保存 聊天记录
	public String saveChatMessage(String appId, String userId, String type,
			String content, String url, String photoType, String photoBase) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setApplicationId(appId);
		chatMessage.setCreatedBy(userId);
		chatMessage.setCreatedTime(new Date());
		chatMessage.setMsgType(type);
		chatMessage.setMsgContent(content);
		chatMessage.setResourceUrl(url);
		// chatMessage.setPhotoBase(photoBase);
		chatMessage.setPhotoType(photoType);
		commonDao.insertObject(chatMessage);
		// System.out.println(chatMessage.getId());
		return chatMessage.getId();
	}

	// base64字符串转化成图片
	public String GenerateImage(String imgStr, String imageType) { // 对字节数组字符串进行Base64解码并生成图片
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String newFileName = IDGenerator.generateID() + imageType;
			String imgFilePath = Constant.FILE_PATH_CHAT + File.separator
					+ newFileName;
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return imgFilePath;
		} catch (Exception e) {
			return null;
		}
	}
}
