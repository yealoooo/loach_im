package cn.loach.service;

import cn.loach.enums.MessageContentTypeEnum;
import cn.loach.message.Message;
import cn.loach.message.SingleChatMessage;

import java.util.UUID;

public class SingleMessageServiceIMpl implements SingleMessageService{

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
    public SingleChatMessage getSendMessageModel(String message) {

        SingleChatMessage singleChatMessage = new SingleChatMessage();
        singleChatMessage.setMessageId(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        singleChatMessage.setContent(message);
        singleChatMessage.setContentType(MessageContentTypeEnum.TEXT);

        return singleChatMessage;
    }
}
