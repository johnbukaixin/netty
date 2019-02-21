package com.ptl.netty.server.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * created by panta on 2019/2/14.
 * ChannelHandler 是给不同类型的事件调用
 * 应用程序实现或扩展 ChannelHandler 挂接到事件生命周期和 提供自定义应用逻辑。
 * @author panta
 */
@ChannelHandler.Sharable//标识这类的实例之间可以在 channel 里面共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 1.创建一个 ByteBuf 保存写的数据
     *
     * 2.创建 Runnable 用于写数据到 channel
     *
     * 3.获取 Executor 的引用使用线程来执行任务
     *
     * 4.手写一个任务，在一个线程中执行
     *
     * 5.手写另一个任务，在另一个线程中执行
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(byteBuf.toString(Charset.forName("UTF-8")));
        //将所接收的消息返回给发送者
//        ctx.write(byteBuf);

        final Channel channel = ctx.channel();
        final ByteBuf buf = Unpooled.copiedBuffer("your data",
                CharsetUtil.UTF_8).retain();    //1
        Runnable writer = new Runnable() {        //2
            public void run() {
                channel.writeAndFlush(buf.duplicate());
            }
        };
        Executor executor = Executors.newCachedThreadPool();//3

       //写进一个线程
        executor.execute(writer);        //4

        //写进另外一个线程
        executor.execute(writer);        //5

        //ByteBuf 上使用了资源池，所以当执行释放资源时可以减少内存的消耗。
        ReferenceCountUtil.release(msg);

    }

    /**
     * 1.创建 ByteBuf 保存写的数据
     *
     * 2.写数据，并刷新
     *
     * 3.添加 ChannelFutureListener 即可写操作完成后收到通知，
     *
     * 4.写操作没有错误完成
     *
     * 5.写操作完成时出现错误
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //冲刷所有待审消息到远程节点。关闭通道后，操作完成

        Channel channel = ctx.channel();

        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);

        ChannelFuture channelFuture = channel.writeAndFlush(buf);

        channelFuture.addListener(new ChannelFutureListener(){

            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {                //4
                    System.out.println("Write successful");
                } else {
                    System.err.println("Write error");    //5
                    future.cause().printStackTrace();
                }
            }
        });
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
