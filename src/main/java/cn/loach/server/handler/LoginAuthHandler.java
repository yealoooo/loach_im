package cn.loach.server.handler;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.session.SessionContainer;
import cn.loach.util.MessageIdGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginAuthHandler extends SimpleChannelInboundHandler<LoginAuthRequestMessage> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginAuthRequestMessage msg) {
        SessionContainer.set(msg.getUserName(), msg.getAuthToken(), ctx);

        LoginAuthResponseMessage loginAuthResponseMessage = new LoginAuthResponseMessage();
        loginAuthResponseMessage.setMessageId(MessageIdGenerator.getMessageId());
        loginAuthResponseMessage.setContentType(MessageContentTypeEnum.TEXT.name());
        loginAuthResponseMessage.setTimeStamp(System.currentTimeMillis());
        loginAuthResponseMessage.setCode(200);
        ctx.writeAndFlush(loginAuthResponseMessage);
    }
}
