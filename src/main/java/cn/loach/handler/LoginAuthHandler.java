package cn.loach.handler;

import cn.loach.message.LoginAuthMessage;
import cn.loach.message.Message;
import cn.loach.message.RequestMessage;
import cn.loach.message.ResponseMessage;
import cn.loach.session.SessionContainer;
import cn.loach.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

import static cn.loach.message.Message.LOGIN_AUTH_MESSAGE_TYPE;

public class LoginAuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Message msg1 = (Message) msg;
        if (msg1.getMessageRequestTypeType() == LOGIN_AUTH_MESSAGE_TYPE) {
            LoginAuthMessage loginAuthMessage = (LoginAuthMessage) msg1;
            // 注册
            String messageFromId = loginAuthMessage.getUserName();
            String authToken = UUID.randomUUID().toString();

            if (StringUtil.isEmpty(messageFromId, authToken)) {
                //处理 登录失败
                ctx.writeAndFlush(error());
                return;
            }

            //TODO 验证TOKEN是否正确

            if (!SessionContainer.set(messageFromId, authToken, ctx)) {
                ctx.writeAndFlush(error());
                return;
            }

            ResponseMessage responseMessage = ResponseMessage.success(200, "Auth Success");
            ctx.writeAndFlush(responseMessage);
        }else {
            RequestMessage requestMessage = (RequestMessage) msg;
            // 验证token
            if (StringUtil.isEmpty(requestMessage.getAuthToken())) {
                ctx.writeAndFlush(error());
                return;
            }

            // 根据token获取用户Id
            String userId = SessionContainer.getUserIdByToken(requestMessage.getAuthToken());
            if (StringUtil.isEmpty(userId)) {
                ctx.writeAndFlush(error());
                return;
            }

            if (!userId.equals(requestMessage.getFromId())) {
                ctx.writeAndFlush(error());
                return;
            }

            // 验证通过 递给下个业务通道
            ctx.fireChannelRead(msg);
        }

        super.channelRead(ctx, msg);
    }

    private ResponseMessage error() {
        return ResponseMessage.error(501, "Auth Error");
    }
}
