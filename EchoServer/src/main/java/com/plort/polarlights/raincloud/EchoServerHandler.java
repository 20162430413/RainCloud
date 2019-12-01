package com.plort.polarlights.raincloud;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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
		System.out.println("Server received：" + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();   //可以使用ctx里的channel写，同样的效果
//		ChannelFuture writeAndFlush = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER); //继续写入
//		ChannelFuture writeAndFlush = channel.writeAndFlush(Unpooled.copiedBuffer("write end",CharsetUtil.UTF_8)); //继续写入
		ChannelFuture writeAndFlush = channel.writeAndFlush(Unpooled.EMPTY_BUFFER); //继续写入
//		writeAndFlush.addListener(ChannelFutureListener.CLOSE);
		writeAndFlush.addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()) {
					System.out.println("write successfully!");
					future.channel().close();
				}else {
					System.out.println("write failed!!");
					future.cause().printStackTrace();
				}
			}
			
		});
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		cause.printStackTrace();
		ctx.close();
		System.out.println("ChannelHandlerContext has closed");
	}
}
