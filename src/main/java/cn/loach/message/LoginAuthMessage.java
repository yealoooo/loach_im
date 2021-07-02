package cn.loach.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginAuthMessage extends Message implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    @Override
    public int getMessageType() {
        return LOGIN_AUTH_MESSAGE_TYPE;
    }
}
