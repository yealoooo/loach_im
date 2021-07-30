package cn.loach.server.initializer;

import cn.loach.server.handler.LengthFieldFrameProtocolHandler;
import cn.loach.server.handler.LoginAuthRequestHandler;
import cn.loach.server.handler.SingleMessageRequestHandler;
import cn.loach.server.handler.SocketChooseHandle;
import cn.loach.server.handler.tcp.TcpServerHandler;
import cn.loach.server.protocol.MessageDecoder;
import cn.loach.server.protocol.MessageEcoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {
//    private final MessageCodec messageCodec = new MessageCodec();
    private final TcpServerHandler tcpServerHandler = new TcpServerHandler();

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
        // websocket、tcp 选择处理器
        ch.pipeline().addLast(new SocketChooseHandle());
        // tcp 链接记录处理器
//        ch.pipeline().addLast("tcp-handler", tcpServerHandler);

        // 粘包半包处理器
        ch.pipeline().addLast("lengthFieldFrameProtocolHandler", new LengthFieldFrameProtocolHandler());
        // 编解码处理器
        ch.pipeline().addLast("messageCodec", new MessageDecoder());
        ch.pipeline().addLast("messageEode", new MessageEcoder());
        // 校验
        ch.pipeline().addLast("authHandler", new LoginAuthRequestHandler());
        // 一对一单聊
        ch.pipeline().addLast("singleChatHandler", new SingleMessageRequestHandler());
    }
}
