package com.plort.polarlights.raincloud;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * inbound :入站
 * @author 李凯建
 *
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received" + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ChannelFuture writeAndFlush = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER); //继续写入
		writeAndFlush.addListener(ChannelFutureListener.CLOSE);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		cause.printStackTrace();
		ctx.close();
		System.out.println("ChannelHandlerContext has closed");
	}
}
