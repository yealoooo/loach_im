package cn.loach.server;

import cn.loach.server.handler.*;
import cn.loach.server.handler.LengthFieldFrameProtocolHandler;
import cn.loach.server.handler.SingleMessageRequestHandler;
import cn.loach.server.protocol.MessageEcoder;
import cn.loach.server.protocol.MessageDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoachTcpServer implements LoachTcpServerInterface{


    public static final LengthFieldFrameProtocolHandler LengthFieldFrameProtocolHandler = new LengthFieldFrameProtocolHandler();

    @Override
    public void init() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    // websocket、tcp 选择处理器
                    ch.pipeline().addLast(new SocketChooseHandle());
                    // 粘包半包处理器
                    ch.pipeline().addLast("lengthFieldFrameProtocolHandler", new LengthFieldFrameProtocolHandler());
                    // byte -> message
                    ch.pipeline().addLast("tcpByteDecode", new MessageDecoder());
                    // message -> byte
                    ch.pipeline().addLast("tcpMessageEcode", new MessageEcoder());
                    // 校验
                    ch.pipeline().addLast("authHandler", new LoginAuthRequestHandler());
                    // 一对一单聊
                    ch.pipeline().addLast("singleChatHandler", new SingleMessageRequestHandler());

                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException ex) {
            log.error("server error :{}", ex.getMessage());
            ex.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        while (true){}
    }
}
