package cn.loach.server.service;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.request.SingleChatRequestMessage;

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
    public SingleChatRequestMessage getSendMessageModel(String message) {

        SingleChatRequestMessage singleChatRequestMessage = new SingleChatRequestMessage();
        singleChatRequestMessage.setMessageId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        singleChatRequestMessage.setContent(message);
        singleChatRequestMessage.setContentType(MessageContentTypeEnum.TEXT);

        return singleChatRequestMessage;
    }
}
