package com.dubbo.framework.serialize;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Protobuf 序列化
 *
 * @author zhou1
 * @since 2021/6/11
 */
public class ProtobufSerializeUtil {

    /**
     * 序列化
     *
     * @param t
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> byte[] serialize(T t, Class<T> clazz) {
        return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    /**
     * 反序列化方法
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T deSerialize(byte[] data, Class<T> clazz) {
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
        T t = runtimeSchema.newMessage();
        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
        return t;
    }
}
