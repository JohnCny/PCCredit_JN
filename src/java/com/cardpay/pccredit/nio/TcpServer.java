package com.cardpay.pccredit.nio;

import java.net.InetAddress;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Service
public class TcpServer implements InitializingBean,DisposableBean{

	private static final Logger logger = Logger.getLogger(TcpServer.class);

	@Autowired
	private MyChannelInitializer myChannelInitializer;

	@Autowired
	private MyServerBootstrap myServerBootstrap;

	@Autowired
	private BossGroup bossGroup;

	@Autowired
	private WorkerGroup workerGroup;

	@Override
	public void afterPropertiesSet() throws Exception {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//initServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public void initServer() throws Exception {
		String runIpAddress = "61.34.0.31";
		myServerBootstrap.group(bossGroup, workerGroup);
		myServerBootstrap.channel(NioServerSocketChannel.class);
		myServerBootstrap.childHandler(myChannelInitializer);
		myServerBootstrap.bind(runIpAddress,10088).sync();
		logger.info("TCP服务器已启动：" + runIpAddress);
		
		//ch.closeFuture().sync();
	}


    @Override
	public void destroy() throws Exception {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}
}
