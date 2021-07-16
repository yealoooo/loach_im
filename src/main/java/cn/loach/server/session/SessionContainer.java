package cn.loach.server.session;

import cn.loach.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionContainer {
    private static final Map<String, String> uidToTokenMap = new HashMap<>();
    private static final Map<String, ChannelHandlerContext> uidToChannelMap = new HashMap<>();
    private static final Map<String, String> channelIdToUid = new HashMap<>();

    private static final Set<String> tcpUserId = new HashSet<>();
    private static final Set<String> webSocketUserId = new HashSet<>();

    public static boolean set(String uid, String token, ChannelHandlerContext ctx) {
        uidToChannelMap.put(uid, ctx);
        uidToTokenMap.put(uid, token);
        channelIdToUid.put(ctx.channel().id().asLongText(), uid);
        return true;
    }

    public static ChannelHandlerContext getChannelByUid(String uid) {
        if (StringUtil.isEmpty(uid)) return null;
        return uidToChannelMap.get(uid);
    }

    public static String getTokenByUid(String uid) {
        if (StringUtil.isEmpty(uid)) return null;
        return uidToTokenMap.get(uid);
    }

    public static String getUidByChannel(ChannelHandlerContext ctx) {
        return channelIdToUid.get(ctx.channel().id().asLongText());
    }

    public static boolean setTcpUid(String uid) {
        return tcpUserId.add(uid);
    }

    public static boolean setWebSocketUid(String uid) {
        return webSocketUserId.add(uid);
    }
}
