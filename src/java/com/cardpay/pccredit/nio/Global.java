package com.cardpay.pccredit.nio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@Service
public class Global {
	//public ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public ConcurrentHashMap<String, ChannelGroup>  channelGroupMap = new ConcurrentHashMap<String, ChannelGroup>();
	public ConcurrentHashMap<String, String> loginClientMap = new ConcurrentHashMap<String, String>();//<channelid,appid>
	
} 