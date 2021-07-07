package cn.loach.util;

import java.util.UUID;

public class MessageIdGenerator {

    public static String getMessageId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
