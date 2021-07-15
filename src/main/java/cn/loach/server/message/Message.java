package cn.loach.server.message;

import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public abstract class Message implements Serializable {

    /**
     * 消息Id  唯一标识
     */
    private String messageId;

    /**
     * 消息发生时间
     */
    private long timeStamp;

    /**
     * 普通  、  撤回   、 已读
     */
    private int messageType;

    /**
     * 消息类型   群聊  单聊  校验
     */
    private int chatType;


    /**
     * chatType
     */
    public static final int SINGLE = 1;
    public static final int GROUP = 2;
    public static final int AUTH = 3;
    /**
     * messageType  所有消息类型
     */
    public static final int MESSAGE_REQUEST_TYPE = 1;  // 发送
    public static final int MESSAGE_RESPONSE_TYPE = 2; // 接收
    public static final int MESSAGE_RETRACT_TYPE = 3; // 撤回
    public static final int MESSAGE_READ_TYPE = 4; // 已读


    /**
     * SINGLE + MESSAGE_REQUEST_TYPE  = (1 << 1) | 1 = 3   一对一发送  3
     * SINGLE + MESSAGE_RESPONSE_TYPE  = (1 << 2) | 2 = 6   一对一发送  6
     *
     * GROUP + MESSAGE_REQUEST_TYPE  = (2 << 1) | 1 = 5   群组发送  5
     * GROUP + MESSAGE_RESPONSE_TYPE  = (2 << 2) | 2 = 10   群组接收 10
     *
     * SINGLE + MESSAGE_RETRACT_TYPE  = (1 << 3) | 3 = 11   单聊撤回  11
     * SINGLE + MESSAGE_READ_TYPE  = (1 << 4) | 4 = 20   单聊已读   20
     *
     * GROUP + MESSAGE_READ_TYPE  = (2 << 3) | 3 = 19   群组撤回  19
     * GROUP + MESSAGE_READ_TYPE  = (2 << 4) | 3 = 36   群组接收  36
     */


    public static final Map<Integer, Class<? extends Message>> messageClassMap = new HashMap<>();

    static {
        messageClassMap.put((SINGLE * 10) + MESSAGE_REQUEST_TYPE, SingleChatRequestMessage.class);
        messageClassMap.put((SINGLE * 10) + MESSAGE_RESPONSE_TYPE, SingleChatResponseMessage.class);
    }


    public static void main(String[] args) {

    }
}
