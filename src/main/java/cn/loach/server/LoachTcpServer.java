package cn.loach.server;

import cn.loach.server.handler.LengthFieldFrameProtocolHandler;
import cn.loach.server.handler.LoginAuthHandler;
import cn.loach.server.handler.SingleMessageRequestHandler;
import cn.loach.server.message.request.RequestMessage;
import cn.loach.server.message.response.ResponseMessage;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.protocol.MessageEcoder;
import cn.loach.server.protocol.MessageDecoder;
import cn.loach.server.service.SingleMessageServiceIMpl;
import cn.loach.server.session.SessionContainer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoachTcpServer implements LoachTcpServerInterface{


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
                    ch.pipeline().addLast(new LengthFieldFrameProtocolHandler());
                    ch.pipeline().addLast(new MessageDecoder());
                    ch.pipeline().addLast(new MessageEcoder());
                    ch.pipeline().addLast(new LoginAuthHandler());
                    ch.pipeline().addLast(new SingleMessageRequestHandler());

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
