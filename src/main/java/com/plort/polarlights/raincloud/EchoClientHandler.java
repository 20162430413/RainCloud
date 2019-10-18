package com.plort.polarlights.raincloud;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
	}
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("client received: "+msg.toString(CharsetUtil.UTF_8));//记录已接受消息的转储
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		cause.printStackTrace();
		ctx.close();
		System.out.println("client's ChannelHandlerContext closed.");
	}

}
