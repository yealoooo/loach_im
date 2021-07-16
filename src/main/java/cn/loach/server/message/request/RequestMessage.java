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
     * 发送方
     */
    private String fromUid;

    /**
     * 目标
     */
    private String toUid;

    /**
     * 数据类型
     */
    private int contentType;

    public void setContentType(MessageContentTypeEnum messageContentTypeEnum) {
        this.contentType = messageContentTypeEnum.ordinal();
    }

}
