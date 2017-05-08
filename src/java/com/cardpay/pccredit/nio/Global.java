package com.cardpay.pccredit.nio;

import org.springframework.stereotype.Service;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@Service
public class Global {
	public ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
} 