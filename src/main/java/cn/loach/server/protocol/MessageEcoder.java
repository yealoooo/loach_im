package cn.loach.server.protocol;

import cn.loach.server.message.Message;
import cn.loach.server.serializable.LoachSerializable;
import cn.loach.server.session.SessionContainer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

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
        // 定义 消息版本  4字节
        byteBuf.writeInt(messageVersion);
        // 定义 序列化方式 4字节
        byteBuf.writeInt(serializableType);
        // 定义 消息类型   4字节
        byteBuf.writeInt(message.getChatType());
        // messageType
        byteBuf.writeInt(message.getMessageType());
        // 获取内容的字节数组
        byte[] dataBytes = LoachSerializable
                .getSerializable(serializableType)
                .serialize(message);

        // 内容长度 4字节
        byteBuf.writeInt(dataBytes.length);
        // 8. 写入内容
        byteBuf.writeBytes(dataBytes);

        if (SessionContainer.isAtWebSocket(ctx)) {
            byteBuf.retain();
            ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
        }
    }
}
