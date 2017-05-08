package com.cardpay.pccredit.nio;

import org.springframework.stereotype.Service;

import io.netty.channel.nio.NioEventLoopGroup;

@Service
public class BossGroup extends NioEventLoopGroup {

	/** 用于分配处理业务线程的线程组个数 */
	private static final int BIZGROUPSIZE = 1;// 一个端口 就那么线程数量为1即可

	public BossGroup() {
		super(BIZGROUPSIZE);
	}

}
