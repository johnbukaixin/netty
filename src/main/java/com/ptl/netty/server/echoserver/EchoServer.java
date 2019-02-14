package com.ptl.netty.server.echoserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * created by panta on 2019/2/14.
 *创建 ServerBootstrap 实例来引导服务器并随后绑定
 * 创建并分配一个 NioEventLoopGroup 实例来处理事件的处理，如接受新的连接和读/写数据。
 * 指定本地 InetSocketAddress 给服务器绑定
 * 通过 EchoServerHandler 实例给每一个新的 Channel 初始化
 * 最后调用 ServerBootstrap.bind() 绑定服务器
 * @author panta
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
//        if (args.length != 1){
//            System.err.println( "Usage: " + EchoServer.class.getSimpleName() + " <port>");
//            return;
//        }

        int port = 8081;

        new EchoServer(port).start();
    }

    private void start() {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();


        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(nioEventLoopGroup)
                //指定使用 NIO 的传输 Channel
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                //在这里我们使用一个特殊的类，ChannelInitializer 。当一个新的连接被接受，一个新的子 Channel 将被创建， ChannelInitializer 会添加我们EchoServerHandler 的实例到 Channel 的 ChannelPipeline。
                // 正如我们如前所述，如果有入站信息，这个处理器将被通知。
                .childHandler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });
        try {
            //绑定的服务器;sync 等待服务器关闭,调用 sync() 的原因是当前线程阻塞
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + channelFuture.channel().localAddress());

            //关闭 channel 和 块，直到它被关闭
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
