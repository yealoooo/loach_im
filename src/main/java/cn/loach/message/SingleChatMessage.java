package cn.loach.message;


import cn.loach.enums.MessageContentTypeEnum;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleChatMessage extends Message implements Serializable {

    /**
     * 发送方
     */
    private String fromId;

    /**
     * 目标
     */
    private String toId;

    /**
     * 数据类型
     */
    private String contentType;

    /**
     * 数据体
     */
    private String content;

    @Override
    public int getMessageType() {
        return SINGLE_CHAT_MESSAGE_REQUEST_TYPE;
    }

    @Override
    public String toString() {

        return "SingleChatMessage{" +
                "fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", content='" + content + '\'' +
                '}' + super.toString();
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public void setContentType(MessageContentTypeEnum messageContentTypeEnum) {
        this.contentType = messageContentTypeEnum.name();
    }

    public void setContent(String content) {
        this.content = content;
    }
}
