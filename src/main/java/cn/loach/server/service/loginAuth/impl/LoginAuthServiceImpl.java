package cn.loach.server.service.loginAuth.impl;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.Message;
import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.service.loginAuth.LoginAuthService;
import cn.loach.server.service.redis.impl.RedisServiceImpl;
import cn.loach.util.MessageIdGenerator;
import cn.loach.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthServiceImpl implements LoginAuthService {

    private static volatile LoginAuthServiceImpl loginAuthService;

    public static LoginAuthServiceImpl getInstance() {
        if (null == loginAuthService) {
            loginAuthService = new LoginAuthServiceImpl();
            synchronized (LoginAuthServiceImpl.class) {
                if (null == loginAuthService) {
                    loginAuthService = new LoginAuthServiceImpl();
                }
            }
        }
        return loginAuthService;
    }


    @Override
    public LoginAuthResponseMessage authLoginData(LoginAuthRequestMessage loginAuthRequestMessage) {

        String authToken = loginAuthRequestMessage.getAuthToken();
        if (StringUtil.isEmpty(authToken)) {
            log.error("token 为空");
            return authError();
        }

        if (RedisServiceImpl.getInstance().existsToken(authToken)) {
            LoginAuthResponseMessage loginAuthResponseMessage = new LoginAuthResponseMessage();
            loginAuthResponseMessage.setCode(200);
            loginAuthResponseMessage.setRequestFlag(true);
            loginAuthResponseMessage.setContentType(MessageContentTypeEnum.TEXT);
            loginAuthResponseMessage.setContent("认证成功");

            return loginAuthResponseMessage;
        }
        return authError();
    }

    private LoginAuthResponseMessage authError() {
        LoginAuthResponseMessage loginAuthResponseMessage = new LoginAuthResponseMessage();
        loginAuthResponseMessage.setCode(501);
        loginAuthResponseMessage.setResponseMessage("认证失败");
        loginAuthResponseMessage.setChatType(Message.AUTH);
        loginAuthResponseMessage.setMessageType(Message.MESSAGE_RESPONSE_TYPE);
        loginAuthResponseMessage.setMessageId(MessageIdGenerator.getMessageId());
        return loginAuthResponseMessage;
    }
}
