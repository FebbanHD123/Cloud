package de.febanhd.cloud.utils;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class ByteBufUtils {

    public static void writeString(ByteBuf byteBuf, String string) {
        byte[] bytes = string.getBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public static String readString(ByteBuf byteBuf) {
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
