package cn.loach.service;

import cn.loach.message.SingleChatMessage;

public interface SingleMessageService {

    SingleChatMessage getSendMessageModel(String message);
}
