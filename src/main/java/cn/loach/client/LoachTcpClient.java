package cn.loach.client;

import cn.loach.enums.MessageContentTypeEnum;
import cn.loach.handler.LengthFieldFrameProtocolHandler;
import cn.loach.message.Message;
import cn.loach.message.SingleChatMessage;
import cn.loach.protocol.MessageCodec;
import cn.loach.protocol.MessageIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoachTcpClient implements LoachTcpClientInterface{
    @Override
    public void init() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new LengthFieldFrameProtocolHandler());
                    ch.pipeline().addLast(new MessageCodec());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            log.error("client error: {}", ex);
        } finally {
            worker.shutdownGracefully();
        }
    }

}
