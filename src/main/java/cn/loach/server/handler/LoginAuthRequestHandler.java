package cn.loach.server.handler;

import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.model.UserInfoModel;
import cn.loach.server.service.loginAuth.LoginAuthService;
import cn.loach.server.service.loginAuth.impl.LoginAuthServiceImpl;
import cn.loach.server.service.redis.impl.RedisServiceImpl;
import cn.loach.server.session.SessionContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthRequestHandler extends SimpleChannelInboundHandler<LoginAuthRequestMessage> {

    private final LoginAuthService loginAuthService = LoginAuthServiceImpl.getInstance();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginAuthRequestMessage msg) {
        LoginAuthResponseMessage loginAuthResponseMessage = loginAuthService.authLoginData(msg);
        if (loginAuthResponseMessage.getCode() == 200) {
            // 保存 用户Id对应的通道
            // 根据token 解析uid
            String token = msg.getAuthToken();

            UserInfoModel userInfoModel = RedisServiceImpl.getInstance().getUserInfoByToken(token);
            SessionContainer.set(userInfoModel, token, ctx);
        }
        ctx.writeAndFlush(loginAuthResponseMessage);
    }
}
