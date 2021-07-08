package cn.loach.server.session;

import cn.loach.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionContainer {
    private static final Map<String, String> tokenMap = new HashMap<>();
    private static final Map<String, String> tokenMapRevere = new HashMap<>();
    private static final Map<String, ChannelHandlerContext> ctxMap = new HashMap<>();


    public static final Set<String> userNameSet = new HashSet<String>(){{
        add("张三");
        add("李四");
    }};

    public static boolean set(String userId, String token, ChannelHandlerContext ctx) {
        tokenMap.put(userId, token);
        ctxMap.put(userId, ctx);
        tokenMapRevere.put(token, userId);

        return true;
    }

    public static ChannelHandlerContext getCtxByToken(String token) {
        String userId = getUserIdByToken(token);
        if (StringUtil.isEmpty(userId)) return null;
        return ctxMap.get(userId);
    }

    public static ChannelHandlerContext getCtxByUserId(String userId) {
        if (StringUtil.isEmpty(userId)) return null;
        return ctxMap.get(userId);
    }

    public static String getUserIdByToken(String token) {
        if (StringUtil.isEmpty(token)) return null;
        return tokenMapRevere.get(token);
    }
}
