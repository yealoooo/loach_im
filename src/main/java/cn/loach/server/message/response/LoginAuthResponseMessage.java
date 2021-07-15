package cn.loach.server.message.response;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.util.MessageIdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginAuthResponseMessage extends ResponseMessage implements Serializable {

    private String content;

    public LoginAuthResponseMessage() {
        setMessageId(MessageIdGenerator.getMessageId());
        setContentType(MessageContentTypeEnum.TEXT);
        setTimeStamp(System.currentTimeMillis());
        setChatType(AUTH);
        setMessageType(MESSAGE_RESPONSE_TYPE);
    }
}
