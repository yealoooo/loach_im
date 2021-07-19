package cn.loach.server.session;

import cn.loach.server.message.Message;
import cn.loach.server.message.request.RequestMessage;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.ResponseMessage;
import cn.loach.server.model.UserInfoModel;
import cn.loach.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;
import java.util.stream.Collectors;

public class SessionContainer {
    private static final Map<String, String> uidToTokenMap = new HashMap<>();
    private static final Map<String, List<ChannelHandlerContext>> uidToChannelMap = new HashMap<>();
    private static final Map<String, String> channelIdToUid = new HashMap<>();
    private static final Map<String, UserInfoModel> uidToUserInfo = new HashMap<>();

    private static final Set<String> tcpChannelId = new HashSet<>();
    private static final Set<String> webSocketChannelId = new HashSet<>();

    public static boolean set(UserInfoModel userInfoModel, String token, ChannelHandlerContext ctx) {
        String uid = userInfoModel.getUid();
        if (uidToChannelMap.containsKey(uid)) {
            uidToChannelMap.get(uid).add(ctx);
        }else {
            uidToChannelMap.put(uid, new ArrayList<ChannelHandlerContext>(){{
                add(ctx);
            }});
        }

        uidToUserInfo.put(uid, userInfoModel);
        uidToTokenMap.put(uid, token);
        channelIdToUid.put(ctx.channel().id().asLongText(), uid);
        return true;
    }

    public static UserInfoModel getUserInfoByUid(String uid) {
        return uidToUserInfo.get(uid);
    }

    public static ChannelHandlerContext getChannelByUid(String uid) {
        if (StringUtil.isEmpty(uid)) return null;
        return null;
    }

    public static String getTokenByUid(String uid) {
        if (StringUtil.isEmpty(uid)) return null;
        return uidToTokenMap.get(uid);
    }

    public static String getUidByChannel(ChannelHandlerContext ctx) {
        return channelIdToUid.get(ctx.channel().id().asLongText());
    }

    public static boolean setTcp(ChannelHandlerContext ctx) {
        return tcpChannelId.add(ctx.channel().id().asLongText());
    }

    public static boolean setWebSocket(ChannelHandlerContext ctx) {
        return webSocketChannelId.add(ctx.channel().id().asLongText());
    }

    public static boolean isAtWebSocket(ChannelHandlerContext ctx) {
        return webSocketChannelId.contains(ctx.channel().id().asLongText());
    }

    public static boolean removeAll(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        tcpChannelId.remove(channelId);
        webSocketChannelId.remove(channelId);
        String uid = channelIdToUid.get(channelId);
        if (!StringUtil.isEmpty(uid)) {
            uidToTokenMap.remove(uid);
            uidToChannelMap.remove(uid);
            channelIdToUid.remove(channelId);
        }
        return true;
    }

    public static boolean send(ResponseMessage message) {
        String toUid = message.getToUid();
        List<ChannelHandlerContext> channelHandlerContexts = uidToChannelMap.get(toUid);
        if (null != channelHandlerContexts) {
            for (ChannelHandlerContext channelHandlerContext : uidToChannelMap.get(toUid)) {
                channelHandlerContext.writeAndFlush(message);
            }
        }
        return true;
    }
}
