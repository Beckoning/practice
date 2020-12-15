package com.pay.payment.center.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    //绑定的端口号
    private int port;

    public NettyServer(int port) {
        this.port = port;
        bind();
    }

    private void bind() {
        //创建线程组
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //定义服务端启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置线程组
            bootstrap.group(boss, worker);
            //使用NioServerSocketChannel  指定Nio的模式，如果是客户端就是NioSocketChannel
            bootstrap.channel(NioServerSocketChannel.class);

            //为每个连接添加NettyServerHandler 处理器
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    // 添加NettyServerHandler，用来处理Server端接收和处理消息的逻辑
                    p.addLast(new NettyServerHandler());

                }
            });
            //绑定端口并进行阻塞
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            if (channelFuture.isSuccess()) {
                System.err.println("启动Netty服务成功，端口号：" + this.port);
            }
            // 关闭连接之前一直阻塞
            channelFuture.channel().closeFuture().sync();

            System.out.println("connect结束");

        } catch (Exception e) {
            System.err.println("启动Netty服务异常，异常信息：" + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("退出的时候关闭线程组");
            //退出的时候关闭线程组
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //启动服务器
        new NettyServer(10086);
    }
}