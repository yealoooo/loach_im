package cn.loach.server.handler;

import cn.loach.server.message.request.SingleChatRequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleMessageRequestHandler extends SimpleChannelInboundHandler<SingleChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatRequestMessage msg) throws Exception {
        log.info("服务端读取到SingleMessageRequest： {}", msg);
    }
}
