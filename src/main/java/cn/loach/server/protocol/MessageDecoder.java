package cn.loach.server.protocol;

import cn.loach.server.message.Message;
import cn.loach.server.serializable.LoachSerializable;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {

        int magicNum = in.readInt();

        int version = in.readInt();

        byte[] appKeyArray = new byte[32];
        in.readBytes(appKeyArray);
        String appKey = new String(appKeyArray, StandardCharsets.UTF_8);

        byte[] appSerectArray = new byte[32];
        in.readBytes(appSerectArray);
        String appSecret = new String(appSerectArray, StandardCharsets.UTF_8);

        int serializerType = in.readInt();

        int messageType = in.readInt();

        int chatType = in.readInt();

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        Message message = LoachSerializable
                .getSerializable(serializerType)
                .deserialize(Message.messageClassMap.get(messageType), bytes);

        log.info("magicNum: {}, version: {}, appKey: {}, appSecret: {}, serializerType: {}, messageType: {}, chatType: {}", magicNum, version, appKey, appSecret, serializerType, messageType, chatType);
        log.info("data: {}", JSON.toJSONString(message));

        list.add(message);
    }
}
