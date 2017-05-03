package com.cardpay.pccredit.nio;

import org.springframework.stereotype.Service;

import io.netty.channel.nio.NioEventLoopGroup;

@Service
public class WorkerGroup extends NioEventLoopGroup {

	/** 业务出现线程大小 */
	private static final int BIZTHREADSIZE = Runtime.getRuntime().availableProcessors() * 2;// nio 少量的work线程即可

	public WorkerGroup() {
		super(BIZTHREADSIZE);
	}
}
