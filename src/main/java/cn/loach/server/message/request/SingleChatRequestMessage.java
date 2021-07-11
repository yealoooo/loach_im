package cn.loach.server.message.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SingleChatRequestMessage extends RequestMessage implements Serializable {
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
}
