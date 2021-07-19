package cn.loach.server.handler;

import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;
import cn.loach.server.service.singleMessage.SingleMessageService;
import cn.loach.server.service.singleMessage.SingleMessageServiceIMpl;
import cn.loach.server.session.SessionContainer;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class SingleMessageRequestHandler extends SimpleChannelInboundHandler<SingleChatRequestMessage> {

    private SingleMessageService singleMessageService = SingleMessageServiceIMpl.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatRequestMessage msg) {
//        log.info("服务端读取到SingleMessageRequest： {}", msg);
//
//        ChannelHandlerContext toUserCtx = SessionContainer.getCtxByUserId(toId);
//        if (null == toUserCtx) {
//            log.error("用户未在线");
//        }else {
        SingleChatResponseMessage singleChatResponseMessage = singleMessageService.getSendMessageModel(msg);

//        ctx.writeAndFlush(singleChatResponseMessage);
        SessionContainer.send(singleChatResponseMessage);
//        }

    }
}
