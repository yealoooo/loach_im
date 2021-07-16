package cn.loach.server.handler;

import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.service.loginAuth.LoginAuthService;
import cn.loach.server.service.loginAuth.impl.LoginAuthServiceImpl;
import cn.loach.server.session.SessionContainer;
import cn.loach.util.TokenUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthRequestHandler extends SimpleChannelInboundHandler<LoginAuthRequestMessage> {

    private final LoginAuthService loginAuthService = LoginAuthServiceImpl.getInstance();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginAuthRequestMessage msg) {
        LoginAuthResponseMessage loginAuthResponseMessage = loginAuthService.authLoginData(msg);
        if (null != loginAuthResponseMessage) {
            if (loginAuthResponseMessage.getCode() == 200) {
                // 保存 用户Id对应的通道
                // 根据token 解析uid
                String token = msg.getAuthToken();
                String uid = TokenUtil.getAppIdAndUid(token);
                SessionContainer.set(uid, token, ctx);

                ctx.writeAndFlush(loginAuthResponseMessage);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("异常断开 {}", ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.error("链接移除 {}", ctx.channel());
    }
}
