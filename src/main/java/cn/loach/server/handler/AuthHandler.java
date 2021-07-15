package cn.loach.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("建立链接: ip: {}, port: {}", socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.warn("客户端断开链接: ip: {}, port: {}", socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
    }
}
