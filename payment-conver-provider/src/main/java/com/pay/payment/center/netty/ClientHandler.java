//package com.pay.paymnet.netty.com.pay.payment.center;
//
//import io.netty.channel.ChannelHandlerAdapter;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.util.ReferenceCountUtil;
//
//public class ClientHandler extends ChannelHandlerAdapter {
//
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		try {
//			System.out.println("client" + msg.toString());
//		} finally {
//			ReferenceCountUtil.release(msg);//释放缓冲区
//		}
//	}
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		cause.printStackTrace();
//		ctx.close();
//	}
//}
