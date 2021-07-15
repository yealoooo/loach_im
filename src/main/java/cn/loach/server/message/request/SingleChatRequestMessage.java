package cn.loach.server.message.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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



    @Override
    public String toString() {

        return "SingleChatMessage{" +
                "fromId='" + super.getFromId() + '\'' +
                ", toId='" + super.getToId() + '\'' +
                ", content='" + content + '\'' +
                '}' + super.toString();
    }
}
