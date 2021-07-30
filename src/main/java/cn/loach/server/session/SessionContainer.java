package cn.loach.server.session;

import cn.loach.server.message.response.ResponseMessage;
import cn.loach.server.model.UserInfoModel;
import cn.loach.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;

public class SessionContainer {
    private static final Map<String, ChannelHandlerContext> uidToChannelMap = new HashMap<>();
    private static final Map<String, String> channelIdToUid = new HashMap<>();
    private static final Map<String, UserInfoModel> uidToUserInfo = new HashMap<>();

    private static final Set<String> tcpChannelId = new HashSet<>();
    private static final Set<String> webSocketChannelId = new HashSet<>();

    public static void set(UserInfoModel userInfoModel, String token, ChannelHandlerContext ctx) {
        String uid = userInfoModel.getUid();
        String useUid = userInfoModel.getAppId() + ":" + uid;
        if (uidToChannelMap.containsKey(useUid)) {
            removeAll(uidToChannelMap.get(useUid));
        }

        uidToChannelMap.put(useUid, ctx);
        uidToUserInfo.put(useUid, userInfoModel);
        channelIdToUid.put(ctx.channel().id().asLongText(), useUid);
    }

    public static UserInfoModel getUserInfoByUid(String uid) {
        return uidToUserInfo.get(uid);
    }

    public static ChannelHandlerContext getChannelByUid(String uid) {
        if (StringUtil.isEmpty(uid)) return null;
        return null;
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
        tcpChannelId.remove(channelId);
        String uid = channelIdToUid.get(channelId);
        if (!StringUtil.isEmpty(uid)) {
            uidToChannelMap.remove(uid);
            channelIdToUid.remove(channelId);
            uidToUserInfo.remove(uid);
        }
        ctx.close();
        return true;
    }

    public static boolean send(ResponseMessage message) {
        String toUid = message.getToUid();
        ChannelHandlerContext channelHandlerContext = uidToChannelMap.get(toUid);
        if (null != channelHandlerContext) {
            channelHandlerContext.writeAndFlush(message);
            return true;
        }
        return false;
    }
}
