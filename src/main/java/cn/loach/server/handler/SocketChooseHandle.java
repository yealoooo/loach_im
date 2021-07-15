package cn.loach.server.handler;

import cn.loach.server.handler.webSocket.WebSocketServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.List;

public class SocketChooseHandle extends ByteToMessageDecoder {

    /** 默认暗号长度为23 */
    private static final int MAX_LENGTH = 23;
    /** WebSocket握手的协议前缀 */
    private static final String WEBSOCKET_PREFIX = "GET /";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        String protocol = getBufStart(in);
        if (protocol.startsWith(WEBSOCKET_PREFIX)) {
            websocketAdd(ctx);
        }

        in.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());
    }

    public  void websocketAdd(ChannelHandlerContext ctx){

        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        ctx.pipeline().addBefore("lengthFieldFrameProtocolHandler","http-codec",new HttpServerCodec());

        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        ctx.pipeline().addBefore("lengthFieldFrameProtocolHandler","aggregator",new HttpObjectAggregator(65535));

        // ChunkedWriteHandler：向客户端发送HTML5文件
        ctx.pipeline().addBefore("lengthFieldFrameProtocolHandler","http-chunked",new ChunkedWriteHandler());

//        ctx.pipeline().addBefore("byteToBuf","WebSocketAggregator",new WebSocketFrameAggregator(65535));
        ctx.pipeline().addBefore("lengthFieldFrameProtocolHandler", "webSocketHandler",new WebSocketServerHandler());//自定义的业务handler
    }


    private String getBufStart(ByteBuf in){
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        // 标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
}
