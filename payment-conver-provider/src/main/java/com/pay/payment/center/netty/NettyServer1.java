//package com.pay.paymnet.netty.com.pay.payment.center;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.DelimiterBasedFrameDecoder;
//import io.netty.handler.codec.string.StringDecoder;
//
//import java.util.concurrent.CountDownLatch;
//
//public class NettyServer {
//
//    public static void main(String[] args) throws InterruptedException {
//        // 第一个线程组是用于接受Client端连接
//        EventLoopGroup bossGroup =new NioEventLoopGroup();
//        // 第二个线程组是用于实际业务处理的
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        ServerBootstrap b = new ServerBootstrap();
//        //绑定两个线程池
//        b.group(bossGroup,workerGroup);
//        //指定Nio的模式，如果是客户端就是NioSocketChannel
//        b.channel(NioServerSocketChannel.class);
//        //TCP 的缓冲区设置
//        b.option(ChannelOption.SO_BACKLOG,1024);
//        //设置发送缓冲区大小
//        b.option(ChannelOption.SO_SNDBUF,32*1024);
//        //设置接受缓冲区大小
//        b.option(ChannelOption.SO_RCVBUF,32*1024);
//        //保持连续
//        b.option(ChannelOption.SO_KEEPALIVE,true);
//
//        b.childHandler(new ChannelInitializer<SocketChannel>() {
//
//
//            @Override
//            protected void initChannel(SocketChannel sc) throws Exception {
//                ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());//拆包粘包定义结束字符串（第一种解决方案）
//                sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));//在管道中加入结束字符串
//                //	sc.pipeline().addLast(new FixedLengthFrameDecoder(200));第二种定长
//                sc.pipeline().addLast(new StringDecoder());//定义接收类型为字符串把ByteBuf转成String
//                sc.pipeline().addLast(new ServertHandler());//在这里配置具体数据接收方法的处理
//
//
//            }
//        });
//
//        new CountDownLatch(1).await();
//
//
//
//
//
//    }
//
//
//}
