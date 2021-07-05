package cn.loach.message;

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
     * 消息类型  默认为 JAVA 序列化
     */
    private int messageType;

    /**
     * 消息发生时间
     */
    private long timeStamp;

    public abstract int getMessageType();

    /**
     * Message  所有消息类型
     */
    public static final int SINGLE_CHAT_MESSAGE_REQUEST_TYPE = 1;
    public static final int SINGLE_CHAT_MESSAGE_RESPONSE_TYPE = 2;

    public static final int LOGIN_AUTH_MESSAGE_TYPE = 3;


    public static final Map<Integer, Class<? extends Message>> messageClassMap = new HashMap<>();

    static {
        messageClassMap.put(SINGLE_CHAT_MESSAGE_REQUEST_TYPE, SingleChatMessage.class);
        messageClassMap.put(SINGLE_CHAT_MESSAGE_RESPONSE_TYPE, SingleChatMessage.class);
    }
}
