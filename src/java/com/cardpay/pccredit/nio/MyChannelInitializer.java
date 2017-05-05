package com.cardpay.pccredit.nio;

import java.nio.charset.Charset;

import org.springframework.stereotype.Service;

import com.cardpay.pccredit.websocket.HttpRequestHandler;
import com.cardpay.pccredit.websocket.TextWebSocketFrameHandler;
import com.wicresoft.util.spring.Beans;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

@Service
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		/*
		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
				Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		// pipeline.addLast("ping", new IdleStateHandler(20, 18, 10));
		// //心跳监测 读超时为10s，写超时为10s 全部空闲时间100s
		 */
		//pipeline.addLast("framer",new DelimiterBasedFrameDecoder(1024*1024,Delimiters.lineDelimiter()));
		pipeline.addLast("http-codec",new HttpServerCodec());
        pipeline.addLast("aggregator",new HttpObjectAggregator(1024*1024));
        pipeline.addLast("http-chunked",new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast("handler",Beans.get(TextWebSocketFrameHandler.class));

	}

}
