package cn.loach.server.message;

import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
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
     * SINGLE + MESSAGE_REQUEST_TYPE  = (1 << 8) | 1    一对一发送
     * SINGLE + MESSAGE_RESPONSE_TYPE  = (1 << 8) | 2   一对一接收
     *
     * GROUP + MESSAGE_REQUEST_TYPE  = (2 << 8) | 1   群组发送
     * GROUP + MESSAGE_RESPONSE_TYPE  = (2 << 8) | 2   群组接收
     *
     * SINGLE + MESSAGE_RETRACT_TYPE  = (1 << 8) | 3   单聊撤回
     * SINGLE + MESSAGE_READ_TYPE  = (1 << 8) | 4   单聊已读
     *
     * GROUP + MESSAGE_READ_TYPE  = (2 << 8) | 3   群组撤回
     * GROUP + MESSAGE_READ_TYPE  = (2 << 8) | 4   群组已读
     */


    public static final Map<Integer, Class<? extends Message>> messageClassMap = new HashMap<>();

    static {
        // 认证
        messageClassMap.put((AUTH << 8) | MESSAGE_REQUEST_TYPE, LoginAuthRequestMessage.class);
        messageClassMap.put((AUTH << 8) | MESSAGE_RESPONSE_TYPE, LoginAuthResponseMessage.class);
        // 一对一
        messageClassMap.put((SINGLE << 8) | MESSAGE_REQUEST_TYPE, SingleChatRequestMessage.class);
        messageClassMap.put((SINGLE << 8) | MESSAGE_RESPONSE_TYPE, SingleChatResponseMessage.class);
        // 群组
//        messageClassMap.put((GROUP << 8) | MESSAGE_REQUEST_TYPE, SingleChatRequestMessage.class);
//        messageClassMap.put((GROUP << 8) | MESSAGE_RESPONSE_TYPE, SingleChatResponseMessage.class);
    }
}
