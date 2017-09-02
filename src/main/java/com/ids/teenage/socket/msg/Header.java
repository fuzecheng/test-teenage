package com.ids.teenage.socket.msg;

import java.io.Serializable;

/**
 * Created by Master.wang on 2017/3/27.
 */ // 定义一个发送消息协议格式：消息头 (|-4字节消息体长度-|-2字节消息ID-|-4字节校验码-|) +  |-消息体-|
public class Header implements Serializable {

    // |-4字节消息体长度-|-2字节消息ID-|-4字节校验码|
    private int msgLength;
    private int msgId;
    private int crc;


    public static final int LENGTH_LEN=4; //4字节消息体长度
    public static final int MSG_ID=2;     //2字节消息ID
    public static final int CRC_32=4;     //4字节消息校验码

    public int  getLength(){
        return DataPacket.HEADER_LENGTH;
    }


    public Header(){}
    public Header(int crc, int msgId, int msgLength) {
        this.crc = crc;
        this.msgId = msgId;
        this.msgLength = msgLength;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }

    @Override
    public String toString() {
        return "Header{" +
                "msgLength=" + msgLength +
                ", msgId=" + msgId +
                ", crc=" + crc +
                '}';
    }
}
