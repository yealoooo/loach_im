package cn.loach.server.service.loginAuth;


import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;

public interface LoginAuthService {

    LoginAuthResponseMessage authLoginData(LoginAuthRequestMessage loginAuthRequestMessage);
}
