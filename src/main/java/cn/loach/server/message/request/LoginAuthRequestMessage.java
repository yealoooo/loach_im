package cn.loach.server.message.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginAuthRequestMessage extends RequestMessage implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * token
     */
    private String token;

    public LoginAuthRequestMessage() {
        setMessageRequestTypeType(LOGIN_AUTH_MESSAGE_REQUEST_TYPE);
    }
}
