package com.ptl.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import sun.nio.ch.FileChannelImpl;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * created by panta on 2019/1/29.
 *
 * @author panta
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected");
    }

    public static void main(String[] args) {
        Channel channel = new NioServerSocketChannel();

        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.3.29",8080))
                .addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {

                        if (future.isSuccess()){
                            ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                        }
                    }
                });
    }
}
