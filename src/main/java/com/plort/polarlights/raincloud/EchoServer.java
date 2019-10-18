package com.plort.polarlights.raincloud;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	
	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		try {
			new EchoServer(5555).start();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("main exception.");
		}
	}
	
	/**
	 * bootstrap:引导，引导程序，启动，自举
	 * pipeline:管道，输油管道，输气管道（通常指底下的），运输
	 * @throws Exception
	 */
	public void start() throws Exception {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			ServerBootstrap b2 = b.group(group);
			ServerBootstrap b3 = b2.channel(NioServerSocketChannel.class);
			ServerBootstrap b4 = b3.localAddress(new InetSocketAddress(port));
			b4.childHandler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch) {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(serverHandler);
				}
			});
			
			ChannelFuture f = b.bind().sync();
			f.channel().closeFuture().sync();
			
		}finally {
			group.shutdownGracefully().sync();
		}
	}
}
