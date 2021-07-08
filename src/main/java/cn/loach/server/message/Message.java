package cn.loach.server.message;

import cn.loach.server.message.request.LoginAuthRequestMessage;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.LoginAuthResponseMessage;
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
     * 消息类型   如: 一对一聊天发送  、 一对一聊天接收
     */
    private int messageRequestTypeType;

    /**
     * Message  所有消息类型
     */
    public static final int SINGLE_CHAT_MESSAGE_REQUEST_TYPE = 1;  // 一对一聊天发送
    public static final int SINGLE_CHAT_MESSAGE_RESPONSE_TYPE = 2; // 一对一聊天接收

    public static final int LOGIN_AUTH_MESSAGE_REQUEST_TYPE = 3;
    public static final int LOGIN_AUTH_MESSAGE_RESPONSE_TYPE = 4;

    public static final Map<Integer, Class<? extends Message>> messageClassMap = new HashMap<>();

    static {
        messageClassMap.put(SINGLE_CHAT_MESSAGE_REQUEST_TYPE, SingleChatRequestMessage.class);
        messageClassMap.put(SINGLE_CHAT_MESSAGE_RESPONSE_TYPE, SingleChatRequestMessage.class);
        messageClassMap.put(LOGIN_AUTH_MESSAGE_REQUEST_TYPE, LoginAuthRequestMessage.class);
        messageClassMap.put(LOGIN_AUTH_MESSAGE_RESPONSE_TYPE, LoginAuthResponseMessage.class);
    }
}
