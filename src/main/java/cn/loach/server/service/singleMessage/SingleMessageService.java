package cn.loach.server.service.singleMessage;

import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;

public interface SingleMessageService {

    SingleChatResponseMessage getSendMessageModel(SingleChatRequestMessage message);
}
