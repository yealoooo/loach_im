package cn.loach.protocol;

import cn.loach.message.Message;
import cn.loach.serializable.LoachSerializable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    public static final int magicNum = 9559;

    public static final int messageVersion = 1;

    public static final int serializableType = 1;
    

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf byteBuf) {
        // 定义 魔数表示  4字节
        byteBuf.writeInt(magicNum);
        // 定义 消息版本  1字节
        byteBuf.writeByte(messageVersion);
        // 定义 序列化方式 1字节
        byteBuf.writeByte(serializableType);
        // 定义 消息类型   4字节
        byteBuf.writeInt(message.getMessageType());
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

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {
        int magicNum = in.readInt();

        byte version = in.readByte();

        byte serializerType = in.readByte();

        int messageType = in.readInt();

        byte[] idByte = new byte[32];
        in.readBytes(idByte);
        String messageId = new String(idByte, StandardCharsets.UTF_8);

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        Message message = LoachSerializable
                .getSerializable(serializableType)
                .deserialize(Message.messageClassMap.get(messageType), bytes);

        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, messageId, length);
        log.debug("{}", message);
        list.add(message);
    }

    public static void main(String[] args) {
    }
}
