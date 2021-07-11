package cn.loach.server.message.response;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.util.MessageIdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginAuthResponseMessage extends ResponseMessage implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * token
     */
    private String token;

    public LoginAuthResponseMessage() {
        setMessageId(MessageIdGenerator.getMessageId());
        setContentType(MessageContentTypeEnum.TEXT);
        setTimeStamp(System.currentTimeMillis());
        setMessageRequestTypeType(LOGIN_AUTH_MESSAGE_RESPONSE_TYPE);
    }
}
