package com.ptl.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.smartcardio.Card;
import java.nio.charset.Charset;

/**
 * created by panta on 2019/2/21.
 *
 * @author panta
 */
public class OperateByteBuf {

    public static void main(String[] args) {
        Charset charset = Charset.forName("utf-8");
        ByteBuf byteBuf = Unpooled.copiedBuffer("a hello practiced netty",charset);

        System.out.println((char)byteBuf.getByte(0));

        int readerIndex = byteBuf.readerIndex();

        int writerIndex = byteBuf.writerIndex();

        byteBuf.setByte(0,(byte)'b');

        System.out.println((char)byteBuf.getByte(0));

        assert readerIndex == byteBuf.readerIndex();                    //6
        assert writerIndex ==  byteBuf.writerIndex();

        System.out.println((char)byteBuf.readByte());
        int readerIndex1 = byteBuf.readerIndex();

        int writerIndex1 = byteBuf.writerIndex();

        byteBuf.writeByte((byte)'b');

        assert readerIndex1 == byteBuf.readerIndex();                    //6
        assert writerIndex1 !=  byteBuf.writerIndex();

    }
}
