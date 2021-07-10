package cn.loach.server.service.loginAuth.impl;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.service.loginAuth.LoginAuthService;
import cn.loach.server.session.SessionContainer;
import cn.loach.util.MessageIdGenerator;

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
        String loginUsername = loginAuthRequestMessage.getUserName();

        if (SessionContainer.userNameSet.contains(loginUsername)) {
            LoginAuthResponseMessage loginAuthResponseMessage = new LoginAuthResponseMessage();
            loginAuthResponseMessage.setMessageId(MessageIdGenerator.getMessageId());
            loginAuthResponseMessage.setContentType(MessageContentTypeEnum.TEXT.name());
            loginAuthResponseMessage.setTimeStamp(System.currentTimeMillis());
            loginAuthResponseMessage.setCode(200);
            loginAuthResponseMessage.setUserName(loginUsername);

            return loginAuthResponseMessage;
        }
        return null;
    }
}
