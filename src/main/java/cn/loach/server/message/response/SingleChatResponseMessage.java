package cn.loach.server.message.response;

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
     * 昵称
     */
    private String fromNickName;

    /**
     * 头像
     */
    private String fromAvatar;

    /**
     * 会话Id
     */
    private String conversationId;


    public SingleChatResponseMessage() {
        setTimeStamp(System.currentTimeMillis());
        setMessageId(MessageIdGenerator.getMessageId());
        setChatType(Message.SINGLE);
        setMessageType(Message.MESSAGE_RESPONSE_TYPE);
    }
}
