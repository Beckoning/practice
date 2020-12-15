package com.pay.payment.center.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.UnsupportedEncodingException;

//设置共享
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //将msg强转成ByteBuf
        ByteBuf buf = (ByteBuf) msg;
        //从ByteBuf中获取消息
        String recieved = getMessage(buf);
        System.err.println("服务器接收到客户端消息：" + recieved);

        try {
            //使用ctx进行发送消息
            ctx.writeAndFlush(getSendByteBuf("你好，客户端"));
            System.err.println("服务器回复消息：你好，客户端");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //客户端端连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("一台客户端已经连接");
    }

    /*
     * 从ByteBuf中获取信息 使用UTF-8编码返回
     */
    private String getMessage(ByteBuf buf) {

        byte[] con = new byte[buf.readableBytes()];
        //将buf中的内容读取到con
        buf.readBytes(con);
        try {
            return new String(con, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("一个客户端的关闭了连接");
    }

    /**
     * 发生异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生了一个异常：" + cause.getMessage());
    }

    //发送消息
    private ByteBuf getSendByteBuf(String message)
            throws UnsupportedEncodingException {
        //字符串转换成byte数组
        byte[] req = message.getBytes("UTF-8");
        //创建ByteBuf
        ByteBuf pingMessage = Unpooled.buffer();
        //向ByteBuf中写入消息
        pingMessage.writeBytes(req);
        //返回pingMessage
        return pingMessage;
    }
}