//package com.pay.paymnet.netty.com.pay.payment.center;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.DelimiterBasedFrameDecoder;
//import io.netty.handler.codec.string.StringDecoder;
//
//public class Client {
//
//	public static void main(String[] args) throws InterruptedException {
//		EventLoopGroup worker = new NioEventLoopGroup();
//		Bootstrap b = new Bootstrap();
//		b.group(worker)
//		.channel(NioSocketChannel.class)
//		.handler(new ChannelInitializer<SocketChannel>() {
//			@Override
//			protected void initChannel(SocketChannel sc) throws Exception {
//				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//				sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
//				sc.pipeline().addLast(new StringDecoder());
//				sc.pipeline().addLast(new ClientHandler());
//			}
//		});
//		ChannelFuture f=b.connect("127.0.0.1",8765).sync();
//		f.channel().writeAndFlush(Unpooled.copiedBuffer(" hi server2$_".getBytes()));
//		f.channel().writeAndFlush(Unpooled.copiedBuffer(" hi server3$_".getBytes()));
//		f.channel().writeAndFlush(Unpooled.copiedBuffer(" hi server4$_".getBytes()));
//		f.channel().closeFuture().sync();
//		worker.shutdownGracefully();
//	}
//}
