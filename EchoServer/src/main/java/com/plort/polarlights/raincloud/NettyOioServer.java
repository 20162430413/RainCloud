package com.plort.polarlights.raincloud;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

public class NettyOioServer implements Server{
	
	int port = 5556;
	
	public NettyOioServer(){
		
	}
	
	public NettyOioServer(int port ){
		this.port = port;
	}

	/**
	 * duplicate: 复制，复写，复印，重复，副本
	 * @throws Exception
	 */
	public void start() throws Exception{
		final ByteBuf buf = Unpooled.unreleasableBuffer(
				Unpooled.copiedBuffer("Hi!\r\n",Charset.forName("UTF-8")));
		EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(eventLoopGroup)
			.channel(OioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			.childHandler(new ChannelInitializer<SocketChannel>(){
				public void initChannel(SocketChannel sh) throws Exception{
					ChannelPipeline channelPipeline = sh.pipeline();
					channelPipeline.addLast(new ChannelInboundHandlerAdapter(){
						public void channelActive(ChannelHandlerContext ctx) throws Exception{
							ChannelFuture channelFuture = ctx.writeAndFlush(buf.duplicate());
							channelFuture.addListener(ChannelFutureListener.CLOSE);
						}
					});
				}
			});
			ChannelFuture f = b.bind().sync();
			f.channel().closeFuture().sync();
		}finally{
			eventLoopGroup.shutdownGracefully().sync();
		}
	}
}
