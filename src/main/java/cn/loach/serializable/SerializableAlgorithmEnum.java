package cn.loach.serializable;

import cn.loach.message.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONWriter;
import io.netty.util.CharsetUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum SerializableAlgorithmEnum implements SerializableInterface {
    JAVA_SERIALIZABLE {
        @Override
        public <T> T deserialize(Class<T> returnClass, byte[] bytes) {

            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                Object targetObj = ois.readObject();

                if (returnClass.isAssignableFrom(targetObj.getClass())) {
                    return returnClass.cast(targetObj);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public <T> byte[] serialize(T object) {
            // 获取内容的字节数组
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                oos = new ObjectOutputStream(bos);

                oos.writeObject(object);
                return bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new byte[0];
        }
    },

    JSON_SERIALIZABLE {
        @Override
        public <T> T deserialize(Class<T> returnClass, byte[] bytes) {
            String dataStr = new String(bytes, StandardCharsets.UTF_8);
            return JSON.parseObject(dataStr, returnClass);
        }

        @Override
        public <T> byte[] serialize(T object) {
            return JSON.toJSONString(object).getBytes(StandardCharsets.UTF_8);
        }
    }
}
