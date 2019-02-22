package com.ptl.netty.server;

import com.ptl.netty.server.echoserver.EchoClientHandler;
import com.ptl.netty.server.echoserver.EchoServerHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * created by panta on 2019/2/22.
 *
 * @author panta
 */
public class OperateChannelPipeline {

    public static void main(String[] args) {
        NioSocketChannel nioSocketChannel = new NioSocketChannel();
        ChannelPipeline channelPipeline = nioSocketChannel.pipeline();

        MsgHandler msgHandler = new MsgHandler();

        channelPipeline.addLast("handler1",msgHandler).addFirst("handler2",new EchoServerHandler()).addLast("handler3",new EchoClientHandler());

        channelPipeline.remove(msgHandler);
        channelPipeline.remove("handler2");
        channelPipeline.replace("handler3","handler4",new MsgHandler());

    }
}
