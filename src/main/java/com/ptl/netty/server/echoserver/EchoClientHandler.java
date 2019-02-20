package com.ptl.netty.server.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * created by panta on 2019/2/14.
 *
 * @author panta
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当channel激活的时候就可以发送消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        //非堆缓冲判断是否支持数组
        if (byteBuf.hasArray()){
            //缓冲转数组
            byte[] bytes = byteBuf.array();
            //获得第一个字节的偏移量
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            //获得可读的字节数量
            int length = byteBuf.readableBytes();

            handleByte(bytes,offset,length);

        }

        //如果不是堆缓冲区，就为直接缓冲区
        //通过免去中间交换的内存拷贝, 提升IO处理速度; 直接缓冲区的内容可以驻留在垃圾回收扫描的堆区以外。
        //DirectBuffer 在 -XX:MaxDirectMemorySize=xxM大小限制下, 使用 Heap 之外的内存, GC对此”无能为力”,也就意味着规避了在高负载下频繁的GC过程对应用线程的中断影响.
        if (!byteBuf.hasArray()){
            //获取字节可读字节数
            int length = byteBuf.readableBytes();

            //分配一个新的数组用来保存
            byte[] bytes = new byte[length];

            //字节复制到数组
            byteBuf.getBytes(byteBuf.readerIndex(),bytes);

            handleByte(bytes,0,length);
        }
        System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));

    }

    private void handleByte(byte[] bytes, int index, int readByte) {
    }
}
