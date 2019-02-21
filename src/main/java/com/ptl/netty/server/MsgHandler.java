package com.ptl.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * created by panta on 2019/1/25.
 * 处理服务器端管道
 * @author panta
 */
public class MsgHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;

        if (in.hasArray()){
            byte[] bytes = in.array();
            for (int i = 0 ; i < bytes.length ; i ++){
                System.out.println(i);
            }
        }

        for (int i = 0 ; i< in.capacity() ; i++){
            byte b = in.getByte(i);
            System.out.println((char) b);
        }
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
