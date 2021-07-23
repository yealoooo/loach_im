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

    private String msg;

    private Date sendTime;

    private String sendStatus;

    private String readStatus;

    private String isPush;

    private String pushStatus;

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
                .msg(singleChatResponseMessage.getContent())
                .sendTime(new Date())
                .fromUid(singleChatResponseMessage.getFromUid())
                .toUid(singleChatResponseMessage.getToUid())
                .conversationId(singleChatResponseMessage.getConversationId()).build();
    }
}
