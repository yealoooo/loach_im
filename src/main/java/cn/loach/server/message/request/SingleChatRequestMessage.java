package cn.loach.server.message.request;


import cn.loach.server.message.Message;
import cn.loach.util.MessageIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SingleChatRequestMessage extends RequestMessage implements Serializable {
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

    private String ext;


    public SingleChatRequestMessage() {
        setMessageId(MessageIdGenerator.getMessageId());
        setChatType(Message.SINGLE);
        setMessageType(Message.MESSAGE_REQUEST_TYPE);
        setTimeStamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {

        return "SingleChatMessage{" +
                "fromId='" + super.getFromUid() + '\'' +
                ", toId='" + super.getToUid() + '\'' +
                ", content='" + content + '\'' +
                '}' + super.toString();
    }
}
