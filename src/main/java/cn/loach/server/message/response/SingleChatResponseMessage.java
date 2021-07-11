package cn.loach.server.message.response;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.util.MessageIdGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleChatResponseMessage extends ResponseMessage{
    /**
     * 数据体
     */
    private String content;

    /**
     * 发送者
     */
    private String fromId;

    /**
     * 接收者
     */
    private String toId;

    public SingleChatResponseMessage() {
        setTimeStamp(System.currentTimeMillis());
        setContentType(MessageContentTypeEnum.TEXT);
        setMessageId(MessageIdGenerator.getMessageId());
        setMessageRequestTypeType(LOGIN_AUTH_MESSAGE_REQUEST_TYPE);
    }
}
