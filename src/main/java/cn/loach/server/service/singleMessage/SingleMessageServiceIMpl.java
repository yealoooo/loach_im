package cn.loach.server.service.singleMessage;

import cn.loach.server.entity.SynchronizeMessageEntity;
import cn.loach.server.enums.MessageContentTypeEnum;
import cn.loach.server.message.Message;
import cn.loach.server.message.request.SingleChatRequestMessage;
import cn.loach.server.message.response.SingleChatResponseMessage;
import cn.loach.server.model.UserInfoModel;
import cn.loach.server.session.SessionContainer;
import cn.loach.util.HttpUtil;
import com.alibaba.fastjson.JSON;

import java.util.Date;
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
        return SingleChatResponseMessage.SingleChatRequestMessageConversionThis(message);
    }

    @Override
    public void synchronizeMessage(SingleChatResponseMessage singleChatResponseMessage) {
        SynchronizeMessageEntity synchronizeMessageEntity = SynchronizeMessageEntity.SingleChatMessageConversationThis(singleChatResponseMessage);

        HttpUtil.post("http://127.0.0.1:8081/message/insert", JSON.toJSONString(synchronizeMessageEntity));
    }
}
