package cn.loach.message;


import cn.loach.enums.MessageContentTypeEnum;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SingleChatMessage extends RequestMessage implements Serializable {
    /**
     * 数据体
     */
    private String content;

    @Override
    public String toString() {

        return "SingleChatMessage{" +
                "fromId='" + super.getFromId() + '\'' +
                ", toId='" + super.getToId() + '\'' +
                ", content='" + content + '\'' +
                '}' + super.toString();
    }

    public SingleChatMessage() {
        setMessageRequestTypeType(SINGLE_CHAT_MESSAGE_REQUEST_TYPE);
    }

    public void setContent(String content) {
        this.content = content;
    }
}
