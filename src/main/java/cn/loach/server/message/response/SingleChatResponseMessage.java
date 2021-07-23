package cn.loach.server.message.response;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.Message;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.model.UserInfoModel;
import cn.loach.server.session.SessionContainer;
import cn.loach.util.MessageIdGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleChatResponseMessage extends ResponseMessage{
    /**
     * 数据体
     */
    private String content;

    /**
     * 昵称
     */
    private String fromNickName;

    /**
     * 头像
     */
    private String fromAvatar;

    /**
     * 会话Id
     */
    private String conversationId;


    public SingleChatResponseMessage() {
        setTimeStamp(System.currentTimeMillis());
        setChatType(Message.SINGLE);
        setMessageType(Message.MESSAGE_RESPONSE_TYPE);
    }

    public static SingleChatResponseMessage SingleChatRequestMessageConversionThis(SingleChatRequestMessage singleChatRequestMessage) {
        SingleChatResponseMessage singleChatResponseMessage = new SingleChatResponseMessage();

        singleChatResponseMessage.setCode(200);
        singleChatResponseMessage.setContentType(MessageContentTypeEnum.TEXT);
        singleChatResponseMessage.setContent(singleChatRequestMessage.getContent());
        singleChatResponseMessage.setRequestFlag(true);
        singleChatResponseMessage.setMessageId(singleChatRequestMessage.getMessageId());

        // from 信息
        singleChatResponseMessage.setFromUid(singleChatRequestMessage.getFromUid());
        UserInfoModel userInfoByUid = SessionContainer.getUserInfoByUid(singleChatRequestMessage.getFromUid());
        singleChatResponseMessage.setFromAvatar(userInfoByUid.getAvatar());
        singleChatResponseMessage.setFromNickName(userInfoByUid.getNickName());

        singleChatResponseMessage.setToUid(singleChatRequestMessage.getToUid());
        singleChatResponseMessage.setConversationId(singleChatRequestMessage.getConversationId());

        return singleChatResponseMessage;
    }
}
