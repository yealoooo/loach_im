package cn.loach.server.message.response;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.Message;
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
        setMessageId(MessageIdGenerator.getMessageId());
        setChatType(Message.SINGLE);
        setMessageType(Message.MESSAGE_RESPONSE_TYPE);
    }
}
