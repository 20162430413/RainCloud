package com.plort.polarlights.raincloud;

public class ServerStart {

	public static void main(String[] args) {
		try {
			
//			开始EchoServer
			new ServerStart().start(EchoServer.class);
			
			//开始一个OioServer
//			new ServerStart().start(NettyOioServer.class);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("main exception.");
		}
	}
	
	public void start(Class<? extends Server> serverClass) {
		try {
			serverClass.newInstance().start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start(Class<? extends Server> serverClass,int port) {
		
		try {
			serverClass.newInstance().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
