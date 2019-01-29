package com.akazam.test.scheme;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

/**
 * @author Alex
 * @ClassName MessageScheme
 * @description
 * @date 2019/1/28 10:44
 */
public class MessageScheme implements Scheme {
    @Override
    public List<Object> deserialize(ByteBuffer ser) {
        String message = byteBufferToString(ser);
        return new Values(message);
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("msg");
    }

    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
