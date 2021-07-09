package cn.loach.server.protocol;

import cn.loach.server.message.Message;
import cn.loach.server.message.response.ResponseMessage;
import cn.loach.server.serializable.LoachSerializable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MessageEcoder extends MessageToByteEncoder<Message> {
    /**
     * 魔数
     */
    private static final int magicNum = 9559;

    /**
     * 数据版本
     */
    private static final int messageVersion = 1;

    /**
     * 序列化类型
     */
    private static final byte serializableType = LoachSerializable.JSON_SERIALIZABLE_TYPE;


    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf byteBuf) {
        // 定义 魔数表示  4字节
        byteBuf.writeInt(magicNum);
        // 定义 消息版本  1字节
        byteBuf.writeByte(messageVersion);
        // 定义 序列化方式 1字节
        byteBuf.writeByte(serializableType);
        // 定义 消息类型   4字节
        byteBuf.writeInt(message.getMessageRequestTypeType());
        // 定义消息唯一标识 32字节
        byteBuf.writeBytes(message.getMessageId().getBytes(StandardCharsets.UTF_8));
        // 获取内容的字节数组
        byte[] dataBytes = LoachSerializable
                .getSerializable(serializableType)
                .serialize(message);

        // 内容长度 4字节
        byteBuf.writeInt(dataBytes.length);
        // 8. 写入内容
        byteBuf.writeBytes(dataBytes);

    }
}
