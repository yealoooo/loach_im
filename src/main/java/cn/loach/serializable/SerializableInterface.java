package cn.loach.serializable;

public interface SerializableInterface {

    // 反序列化方法
    <T> T deserialize(Class<T> returnClass, byte[] bytes);

    // 序列化方法
    <T> byte[] serialize(T object);
}
