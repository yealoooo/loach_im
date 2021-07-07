package cn.loach.handler;

import cn.loach.message.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RequestMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestMessage requestMessage = (RequestMessage) msg;
        System.out.println(requestMessage.getAuthToken());
        System.out.println(requestMessage.getFromId());
        System.out.println(requestMessage.getToId());
        System.out.println(requestMessage.getContentType());
    }
}
