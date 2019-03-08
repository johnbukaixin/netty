package com.ptl.netty.server.echoserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * created by panta on 2019/2/14.
 *创建一个 Bootstrap 来初始化客户端
 * 一个 NioEventLoopGroup 实例被分配给处理该事件的处理，这包括创建新的连接和处理入站和出站数据
 * 创建一个 InetSocketAddress 以连接到服务器
 * 连接好服务器之时，将安装一个 EchoClientHandler 在 pipeline
 * 之后 Bootstrap.connect（）被调用连接到远程的 - 本例就是 echo(回声)服务器。
 * @author panta
 */
public class EchoClient {

    private String host;

    private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 8081;

        String host = "localhost";

        new EchoClient(host,port).start();
    }

    private void start() {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(nioEventLoopGroup)
                .remoteAddress(new InetSocketAddress(port))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("handler",new EchoClientHandler())
                                .addLast("decoder",new ToIntegerDecoder());
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect().sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                nioEventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
