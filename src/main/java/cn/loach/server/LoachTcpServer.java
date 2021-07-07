package cn.loach.server;

import cn.loach.handler.LengthFieldFrameProtocolHandler;
import cn.loach.handler.LoginAuthHandler;
import cn.loach.message.SingleChatMessage;
import cn.loach.protocol.MessageCodec;
import cn.loach.service.SingleMessageServiceIMpl;
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
//                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new LengthFieldFrameProtocolHandler());
                    ch.pipeline().addLast(new MessageCodec());
                    ch.pipeline().addLast(new LoginAuthHandler());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            log.info("服务端读取到数据:{}", msg.toString());

                            SingleMessageServiceIMpl singleMessageServiceIMpl = SingleMessageServiceIMpl.getInstance();
                            SingleChatMessage singleChatMessage = singleMessageServiceIMpl.getSendMessageModel("你也好");
                            ctx.writeAndFlush(singleChatMessage);
                        }
                    });

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
