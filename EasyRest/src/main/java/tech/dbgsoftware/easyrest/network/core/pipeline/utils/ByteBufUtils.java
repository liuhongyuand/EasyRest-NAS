package tech.dbgsoftware.easyrest.network.core.pipeline.utils;

import io.netty.buffer.ByteBuf;
import tech.dbgsoftware.easyrest.utils.LogUtils;

public class ByteBufUtils {

    public static byte[] readAll(ByteBuf byteBuf) {
        try {
            byte[] contentByte = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(contentByte);
            return contentByte;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
        return new byte[0];
    }
}