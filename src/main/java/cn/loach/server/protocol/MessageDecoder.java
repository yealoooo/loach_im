package cn.loach.server.protocol;

import cn.loach.server.message.Message;
import cn.loach.server.serializable.LoachSerializable;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {

        int magicNum = in.readInt();

        int version = in.readInt();

        int serializerType = in.readInt();

        int chatType = in.readInt();

        int messageType = in.readInt();

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        Message message = LoachSerializable
                .getSerializable(serializerType)
                .deserialize(Message.messageClassMap.get((chatType << 8) | messageType), bytes);
        log.info("magicNum: {}, version: {}, serializerType: {}, messageType: {}, chatType: {}", magicNum, version, serializerType, messageType, chatType);
        log.info("data: {}", JSON.toJSONString(message));

        list.add(message);
    }
}
