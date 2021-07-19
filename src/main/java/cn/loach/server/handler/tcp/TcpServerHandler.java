package cn.loach.server.handler.tcp;

import cn.loach.server.session.SessionContainer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      log.info("tcp 链接 -- {}", ctx);
        SessionContainer.setTcp(ctx);
        ctx.fireChannelActive();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        log.info("tcp  取消注册");
        SessionContainer.removeAll(ctx);
        ctx.fireChannelUnregistered();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("tcp  链接异常断开");
        SessionContainer.removeAll(ctx);
        ctx.fireExceptionCaught(cause);
    }
}
