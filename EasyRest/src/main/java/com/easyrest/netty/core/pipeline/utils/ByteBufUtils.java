package com.easyrest.netty.core.pipeline.utils;
import io.netty.buffer.ByteBuf;

public class ByteBufUtils {

    public static byte[] readAll(ByteBuf byteBuf) {
        byte[] contentByte = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(contentByte);
        return contentByte;
    }
}