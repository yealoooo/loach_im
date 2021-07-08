package cn.loach.server.serializable;

import java.util.HashMap;
import java.util.Map;

public class LoachSerializable {
    public static final byte JAVA_SERIALIZABLE_TYPE = 1;
    public static final byte JSON_SERIALIZABLE_TYPE = 2;

    private static final Map<Byte, SerializableAlgorithmEnum> serializableTypeMap = new HashMap<>();

    static {
        serializableTypeMap.put(JAVA_SERIALIZABLE_TYPE, SerializableAlgorithmEnum.JAVA_SERIALIZABLE);
        serializableTypeMap.put(JSON_SERIALIZABLE_TYPE, SerializableAlgorithmEnum.JSON_SERIALIZABLE);
    }


    public static SerializableAlgorithmEnum getSerializable(byte serializableType) {
        return serializableTypeMap.get(serializableType);
    }
}
