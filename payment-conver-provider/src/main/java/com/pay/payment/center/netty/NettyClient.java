package com.pay.payment.center.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    /*
     * 服务器端口号
     */
    private int port;

    /*
     * 服务器IP
     */
    private String host;

    public NettyClient(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() throws InterruptedException {
        //创建线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            //定义客户端启动类
            Bootstrap bootstrap = new Bootstrap();
            //设置NioSocketChannel
            bootstrap.channel(NioSocketChannel.class);
            //设置线程组
            bootstrap.group(eventLoopGroup);
            //设置服务器的IP以及端口号
            bootstrap.remoteAddress(host, port);
            //设置处理类
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    //添加处理类
                    socketChannel.pipeline().addLast(new NettyClientHandler());
                }
            });
            //连接到服务器并进行阻塞
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("连接服务器成功");
            }
            //客户端关闭之前一直保持阻塞状态
            channelFuture.channel().closeFuture().sync();
        } finally {
            //客户端关闭的时候关闭线程组
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient(10086, "localhost");
    }
}