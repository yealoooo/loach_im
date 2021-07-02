package cn.loach.protocol;

import java.util.UUID;

public class MessageIdGenerator {

    public static String getMessageId() {

        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
}
