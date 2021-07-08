package cn.loach.server.service;

import cn.loach.server.message.request.SingleChatRequestMessage;

public interface SingleMessageService {

    SingleChatRequestMessage getSendMessageModel(String message);
}
