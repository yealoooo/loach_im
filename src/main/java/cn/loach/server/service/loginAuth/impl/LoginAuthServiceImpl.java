package cn.loach.server.service.loginAuth.impl;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.service.loginAuth.LoginAuthService;
import cn.loach.server.session.SessionContainer;

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
        LoginAuthResponseMessage loginAuthResponseMessage = new LoginAuthResponseMessage();

        String authToken = loginAuthRequestMessage.getAuthToken();
        if (authToken.equals("token")) {
            loginAuthResponseMessage.setCode(200);
            loginAuthResponseMessage.setContentType(MessageContentTypeEnum.TEXT);
            loginAuthResponseMessage.setContent("认证成功");

            return loginAuthResponseMessage;
        }
        return null;
    }
}
