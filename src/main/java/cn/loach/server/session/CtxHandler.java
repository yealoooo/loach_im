package cn.loach.server.session;

import io.netty.channel.ChannelHandlerContext;

public class CtxHandler {
    private ChannelHandlerContext ctx;

    public CtxHandler(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void close() {
        ctx.close();
    }
}
