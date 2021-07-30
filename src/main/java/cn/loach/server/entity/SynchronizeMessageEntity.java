package cn.loach.server.entity;

import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;
import cn.loach.server.model.UserInfoModel;
import cn.loach.server.session.SessionContainer;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SynchronizeMessageEntity {
    private String messageId;

    private Integer appId;

    private String toUid;

    private String fromUid;

    private Integer chatType;

    private Integer msgType;

    private String content;

    private Date sendTime;

    private Integer sendStatus;

    private Integer readStatus;

    private Integer isPush;

    private Integer pushStatus;

    private Integer contentType;

    private String conversationId;


    public static SynchronizeMessageEntity SingleChatMessageConversationThis(SingleChatResponseMessage singleChatResponseMessage) {
        UserInfoModel userInfoByUid = SessionContainer.getUserInfoByUid(singleChatResponseMessage.getFromUid());
        Integer appId = userInfoByUid.getAppId();

        return SynchronizeMessageEntity.builder()
                .messageId(singleChatResponseMessage.getMessageId())
                .appId(appId)
                .chatType(singleChatResponseMessage.getChatType())
                .contentType(singleChatResponseMessage.getContentType())
                .content(singleChatResponseMessage.getContent())
                .sendTime(new Date())
                .fromUid(splitUid(singleChatResponseMessage.getFromUid()))
                .toUid(splitUid(singleChatResponseMessage.getToUid()))
                .conversationId(singleChatResponseMessage.getConversationId()).build();
    }

    private static String splitUid(String appIdAndUid) {
        return appIdAndUid.contains(":") ? appIdAndUid.split(":")[1] : appIdAndUid;
    }
}
