package cn.loach.server.service.singleMessage;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;

import java.util.UUID;

public class SingleMessageServiceIMpl implements SingleMessageService {

    private static volatile SingleMessageServiceIMpl singleMessageServiceIMpl;

    public static SingleMessageServiceIMpl getInstance() {
        if (null == singleMessageServiceIMpl) {
            singleMessageServiceIMpl = new SingleMessageServiceIMpl();
            synchronized (SingleMessageServiceIMpl.class) {
                if (null == singleMessageServiceIMpl) {
                    singleMessageServiceIMpl = new SingleMessageServiceIMpl();
                }
            }
        }
        return singleMessageServiceIMpl;
    }

    @Override
    public SingleChatResponseMessage getSendMessageModel(SingleChatRequestMessage message) {

        SingleChatResponseMessage singleChatResponseMessage = new SingleChatResponseMessage();
        singleChatResponseMessage.setMessageId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        singleChatResponseMessage.setContent(message.getContent());
        singleChatResponseMessage.setContentType(MessageContentTypeEnum.TEXT.name());
        singleChatResponseMessage.setFromId(message.getFromId());
        singleChatResponseMessage.setToId(message.getToId());

        return singleChatResponseMessage;
    }
}
