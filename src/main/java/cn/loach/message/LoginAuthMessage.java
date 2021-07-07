package cn.loach.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginAuthMessage extends Message implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * token
     */
    private String token;

    public LoginAuthMessage() {
        setMessageRequestTypeType(LOGIN_AUTH_MESSAGE_TYPE);
    }
}
