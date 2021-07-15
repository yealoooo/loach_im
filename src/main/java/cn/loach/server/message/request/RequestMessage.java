package cn.loach.server.message.request;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RequestMessage extends Message implements Serializable {

    /**
     * token
     */
    private String authToken;

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
    private int contentType;

    public void setContentType(MessageContentTypeEnum messageContentTypeEnum) {
        this.contentType = messageContentTypeEnum.ordinal();
    }

}
