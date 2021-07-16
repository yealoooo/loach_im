package cn.loach.server.service.singleMessage;

import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.Message;
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
        singleChatResponseMessage.setCode(200);
        singleChatResponseMessage.setContentType(MessageContentTypeEnum.TEXT);
        singleChatResponseMessage.setFromId(message.getFromUid());
        singleChatResponseMessage.setToId(message.getToUid());
        singleChatResponseMessage.setContent(message.getContent());
        singleChatResponseMessage.setRequestFlag(true);

        return singleChatResponseMessage;
    }
}
