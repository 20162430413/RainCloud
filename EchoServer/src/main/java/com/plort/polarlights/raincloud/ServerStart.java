package com.plort.polarlights.raincloud;

public class ServerStart {

	public static void main(String[] args) {
		try {
//			开始EchoServer
//			new EchoServer(5555).start(); 
			//开始一个OioServer
			new NettyOioServer(5556).start();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("main exception.");
		}
	}
}
