package com.plort.polarlights.raincloud;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

public class EchoClient {

	private String host;
	private Integer port;

	public EchoClient(String host, Integer port) {
		this.host = host;
		this.port = port;
	}
	
	public static void main(String[] args){
		try{
			new EchoClient("127.0.0.1",5555).start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start() throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.remoteAddress(new InetSocketAddress(host,port));
			b.handler(new ChannelInitializer<SocketChannel>(){
				public void initChannel(SocketChannel ch) throws Exception{
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new EchoClientHandler());
				}
			});
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
		}finally{
			
		}
	}
}
