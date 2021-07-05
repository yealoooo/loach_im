package cn.loach.client;

import cn.loach.enums.MessageContentTypeEnum;
import cn.loach.handler.LengthFieldFrameProtocolHandler;
import cn.loach.message.Message;
import cn.loach.message.SingleChatMessage;
import cn.loach.protocol.MessageCodec;
import cn.loach.protocol.MessageIdGenerator;
import cn.loach.service.SingleMessageServiceIMpl;
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
import java.util.Scanner;

@Slf4j
public class LoachTcpClient implements LoachTcpClientInterface{
    Scanner scanner = new Scanner(System.in);

    @Override
    public void init(String host, int port) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new LengthFieldFrameProtocolHandler());
                    ch.pipeline().addLast(new MessageCodec());
                    ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {
                            new Thread(() -> {
                                while (true) {
                                    System.out.println("请输入需要发送的消息");
                                    String next = scanner.next();

                                    SingleMessageServiceIMpl singleMessageServiceIMpl = SingleMessageServiceIMpl.getInstance();
                                    SingleChatMessage sendMessage = singleMessageServiceIMpl.getSendMessageModel(next);

                                    ctx.writeAndFlush(sendMessage);
                                }
                            }, "client handler thread").start();
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("客户端读取到的数据:{}", msg.toString());
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            log.error("client error: {}", ex.getMessage());
            ex.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

}
