package cn.loach.client;

import cn.loach.enums.MessageContentTypeEnum;
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
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            super.channelActive(ctx);

                            SingleChatMessage singleChatMessage = new SingleChatMessage();
                            singleChatMessage.setMessageId(MessageIdGenerator.getMessageId());
                            singleChatMessage.setMessageType(singleChatMessage.getMessageType());
                            singleChatMessage.setFromId("123");
                            singleChatMessage.setToId("234");
                            singleChatMessage.setContent("hello 你好");
                            singleChatMessage.setContentType(MessageContentTypeEnum.TEXT);

                            ByteBuf buffer = ctx.alloc().buffer(1024);


                            encode(singleChatMessage, buffer);

                            ctx.writeAndFlush(buffer);
                            ctx.channel().close();
                        }
                    });
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

    private static void encode(Message message, ByteBuf byteBuf) throws IOException {

        // 定义 魔数表示  4字节
        byteBuf.writeInt(9559);
        // 定义 消息版本  1字节
        byteBuf.writeByte(1);
        // 定义 序列化方式 1字节
        byteBuf.writeByte(1);
        // 定义 消息类型   4字节
        byteBuf.writeInt(message.getMessageType());
        // 定义消息唯一标识 32字节
        byteBuf.writeBytes(message.getMessageId().getBytes(StandardCharsets.UTF_8));
        // 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();
        // 内容长度 4字节
        byteBuf.writeInt(bytes.length);
        // 8. 写入内容
        byteBuf.writeBytes(bytes);
    }
}
