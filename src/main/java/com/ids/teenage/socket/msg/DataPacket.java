package com.ids.teenage.socket.msg;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Master.wang on 2017/3/27.
 */
public class DataPacket implements Serializable {

    public static final int HEADER_LENGTH=4;         //头部长度为4个字节
    public static final int MIN_PACK_SIZE = 4;       // 最小包长度
    public static final int MAX_PACK_SIZE = 1024*2;    // 最大包长度

    private Header header;
    private byte [] bodys;

    public DataPacket (){}

    public DataPacket(Header header, byte[] bodys) {
        this.header = header;
        this.bodys = bodys;
    }


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getBodys() {
        return bodys;
    }

    public void setBodys(byte[] bodys) {
        this.bodys = bodys;
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "header=" + header +
                ", bodys=" + Arrays.toString(bodys) +
                '}';
    }
}
