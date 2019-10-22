package com.plort.polarlights.raincloud;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * dumplicate: 复制，重写，再做一次
 * @author huayu
 *
 */
public class Test {

	public static void main(String[] args){
		byte[] b =null;
		ByteBuf buf =Unpooled.copiedBuffer("hello world",CharsetUtil.UTF_8);
		if(buf.hasArray()){
			b = buf.array();
			int offset = buf.arrayOffset()+buf.readerIndex();
			int readableBytes = buf.readableBytes();
			System.out.println("第一个字节的偏移量："+offset+" 可读的字节数："+readableBytes);
		}
		
		for(int i = 0;i<buf.capacity();i++){
			byte by = buf.getByte(i);
			System.out.print((char)by);
		}
		
		/**
		 * 读取所有可以读的字节
		 */
		Random r = new Random();
		int a2 = r.nextInt();
//		Character c = '的';
//		System.out.println(Integer.toBinaryString((int)c));
		
		while(buf.isReadable()){
			System.out.print((char)buf.readByte());
		}
		
		System.out.println(" ");
		
		ByteBuf directBuf = Unpooled.directBuffer();
		directBuf.setBytes(directBuf.readerIndex(), b);
		if(!directBuf.hasArray()){
			int length = directBuf.readableBytes();
			byte[] a = new byte[length];
			directBuf.getBytes(directBuf.readerIndex(), a);
			System.out.println("readableBytes: "+length+" a.length: "+a.length);
		}
		
		ByteBuf sliceBuf = Unpooled.copiedBuffer("I'm a person! How are you?",CharsetUtil.UTF_8);
		ByteBuf slicedBuf = sliceBuf.slice(0,13);
		System.out.println("slicedBuf :"+slicedBuf.toString(CharsetUtil.UTF_8));
		slicedBuf.setByte(0,(byte)'J');
		System.out.println("sliceBuf: "+sliceBuf.toString(CharsetUtil.UTF_8));
		ByteBuf copiedBuf = sliceBuf.copy(14,sliceBuf.readableBytes());
		copiedBuf.setByte(0, (byte)'Y');
		System.out.println("copiedBuf: "+copiedBuf.toString(CharsetUtil.UTF_8)); 
		System.out.println("sliceBuf: "+sliceBuf.toString(CharsetUtil.UTF_8));
		
		/**
		 * 引用计数
		 */
		assert sliceBuf.refCnt() != 1;
		System.out.println("引用计数： "+sliceBuf.refCnt()+";"+slicedBuf.refCnt()+";"+copiedBuf.refCnt());
		sliceBuf.release();
		System.out.println("引用计数： "+sliceBuf.refCnt()+";"+slicedBuf.refCnt()+";"+copiedBuf.refCnt());

		
	}
}
